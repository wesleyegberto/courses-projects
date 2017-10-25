import pandas as pd

# Le o CSV e cria o data frame
data_frame = pd.read_csv('buscas.csv')

# Le as colunas de dados
X_df = data_frame[['home', 'busca', 'logado']]
# Le a coluna de resultado
Y_df = data_frame['comprou']

# Extrai os dummies para substituir os dados categoricos
Xdummies_df = pd.get_dummies(X_df).astype(int)

# Extrai o array de dados e resultados
X = Xdummies_df.values
Y = Y_df.values

# Utiliza 90% dos dados para treino e 10% para teste
porcentagem_treino = 0.9
tamanho_treino = int(porcentagem_treino * len(Y))
tamanho_teste = len(Y) - tamanho_treino

treino_dados = X[:tamanho_treino]
treino_marcacoes = Y[:tamanho_treino]

teste_dados = X[-tamanho_teste:]
teste_marcacoes = Y[-tamanho_teste:]


# Cria o modelo para treino
from sklearn.naive_bayes import MultinomialNB
modelo = MultinomialNB()
modelo.fit(treino_dados, treino_marcacoes)

# efetua predicao
resultado = modelo.predict(teste_dados)

diferencas = resultado - teste_marcacoes

acertos = [d for d in diferencas if d == 0]

total_acertos = len(acertos)
total_elementos = len(teste_dados)

taxa_acerto = 100.0 * total_acertos / total_elementos

print('%d%% de %d' % (taxa_acerto, total_elementos))