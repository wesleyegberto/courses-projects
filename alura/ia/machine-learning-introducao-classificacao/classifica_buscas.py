from collections import Counter
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

# Taxa de acerto do algoritmo base (burro)
# Forma bruta de contagem
# acertos_um = len(Y[Y==1])
# acertos_zero = len(Y[Y==0])
# taxa_acerto_base = 100.0 * max(acertos_um, acertos_zero) / len(Y)
taxa_acerto_base = 100.0 * max(Counter(Y).itervalues()) / len(Y)

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
# Compara se o resultado e igual ao esperado
diferencas = (resultado == teste_marcacoes)
# No python True = 1, entao somando temos o total de iguais
total_acertos = sum(diferencas)
total_elementos = len(teste_dados)

taxa_acerto = 100.0 * total_acertos / total_elementos

print('Total elementos: %d' % total_elementos)
print('Taxa acerto base: %f' % taxa_acerto_base)
print('Taxa acerto: %f' % taxa_acerto)