from collections import Counter
import pandas as pd

# Le o arquivo CSV e retorna o X e Y
def read_csv(arquivo):
    # Le o CSV e cria o data frame
    data_frame = pd.read_csv(arquivo)
    # Le as colunas de dados
    X_df = data_frame[['home', 'busca', 'logado']]
    # Le a coluna de resultado
    Y_df = data_frame['comprou']

    # Extrai os dummies para substituir os dados categoricos
    Xdummies_df = pd.get_dummies(X_df).astype(int)

    # Extrai o array de dados e resultados
    return Xdummies_df.values, Y_df.values


# Efetua o treino e teste de um modelo
def fit_and_predict(nome, modelo, treino_dados, treino_marcacoes, teste_dados, teste_marcacoes):
    modelo.fit(treino_dados, treino_marcacoes)

    # efetua predicao
    resultado = modelo.predict(teste_dados)

    # Compara se o resultado e igual ao esperado
    diferencas = (resultado == teste_marcacoes)
    # No python True = 1, entao somando temos o total de iguais
    total_acertos = sum(diferencas)
    total_elementos = len(teste_dados)

    taxa_acerto = 100.0 * total_acertos / total_elementos

    print('Taxa acerto do %s: %f' % (nome, taxa_acerto))

# Carrega os dados do CSV
X, Y = read_csv('buscas.csv')

# Utiliza 90% dos dados para treino e 10% para teste
porcentagem_treino = 0.9
tamanho_treino = int(porcentagem_treino * len(Y))
tamanho_teste = len(Y) - tamanho_treino

treino_dados = X[:tamanho_treino]
treino_marcacoes = Y[:tamanho_treino]

teste_dados = X[-tamanho_teste:]
teste_marcacoes = Y[-tamanho_teste:]

total_elementos = len(teste_dados)
# Taxa de acerto do algoritmo base (burro)
taxa_acerto_base = 100.0 * max(Counter(teste_marcacoes).itervalues()) / len(teste_marcacoes)

print('Total elementos: %d' % total_elementos)
print('Taxa acerto base: %f' % taxa_acerto_base)

# Cria os modelos e executam os treinos e testes para comparacao
from sklearn.naive_bayes import MultinomialNB
fit_and_predict('Naive Bayes', MultinomialNB(), treino_dados, treino_marcacoes, teste_dados, teste_marcacoes)

from sklearn.ensemble import AdaBoostClassifier
fit_and_predict('Adapting Boost', AdaBoostClassifier(), treino_dados, treino_marcacoes, teste_dados, teste_marcacoes)
