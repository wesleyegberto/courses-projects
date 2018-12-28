#!-*- encoding: utf-8 -*-

"""
Código 1:
Código para tentar prever a situação do cliente com base no seu comportamento de acessos.

Colunas CSV com as features:
- Último acesso: Visitou ontém (1 dia atrás).
- Frequência de acesso: Visitou 4 dias.
- Se inscreveu: 4 semanas atrás.
- Está se sentido: Alegre.

Situações:
- Alegre: 2
- Neutro: 1
- Chateado: 0
"""

import pandas as pd
from collections import Counter

# Leitura do CSV para um dataframe do pandas
df = pd.read_csv('situacao_cliente.csv')

# Extrai as colunas com as features
X_df = df[['recencia', 'frequencia', 'semanas_de_inscricao']]
# Extrai as colunas com as labels
Y_df = df['situacao']

# Converte as features categorizadas em números
Xdummies_df = pd.get_dummies(X_df)
Ydummies_df = Y_df

X = Xdummies_df.values
Y = Ydummies_df.values

porcentagem_de_treino = 0.8
porcentagem_de_teste = 0.1

# Separa a massa de treino
tamanho_de_treino = int(porcentagem_de_treino * len(Y))
tamanho_de_teste = int(porcentagem_de_teste * len(Y))
tamanho_de_validacao = len(Y) - tamanho_de_treino - tamanho_de_teste

# Separa a massa de treino
treino_dados = X[:tamanho_de_treino]
treino_marcacoes = Y[:tamanho_de_treino]

fim_de_treino = tamanho_de_treino + tamanho_de_teste

# Separa a massa para teste após o fit
teste_dados = X[tamanho_de_treino:fim_de_treino]
teste_marcacoes = Y[tamanho_de_treino:fim_de_treino]

# Separa a massa de validação
validacao_dados = X[fim_de_treino:]
validacao_marcacoes = Y[fim_de_treino:]

# Treina e testa o modelo
def fit_and_predict(nome, modelo, treino_dados, treino_marcacoes, teste_dados, teste_marcacoes):
    modelo.fit(treino_dados, treino_marcacoes)

    resultado = modelo.predict(teste_dados)

    acertos = resultado == teste_marcacoes

    total_de_acertos = sum(acertos)
    total_de_elementos = len(teste_dados)

    taxa_de_acerto = 100.0 * total_de_acertos / total_de_elementos

    msg = "Taxa de acerto do algoritmo {0}: {1}".format(nome, taxa_de_acerto)

    print(msg)
    return taxa_de_acerto

# Testa o modelo com a massa de validação
def teste_real(modelo, validacao_dados, validacao_marcacoes):
    resultado = modelo.predict(validacao_dados)
    acertos = resultado == validacao_marcacoes

    total_de_acertos = sum(acertos)
    total_de_elementos = len(validacao_marcacoes)

    taxa_de_acerto = 100.0 * total_de_acertos / total_de_elementos

    msg = "Taxa de acerto do vencedor entre os dois algoritmos no mundo real: {0}".format(taxa_de_acerto)
    print(msg)

resultados = {}

from sklearn.naive_bayes import MultinomialNB
modeloMultinomial = MultinomialNB()
resultadoMultinomial = fit_and_predict("MultinomialNB", modeloMultinomial, treino_dados, treino_marcacoes, teste_dados, teste_marcacoes)
resultados[resultadoMultinomial] = modeloMultinomial

from sklearn.ensemble import AdaBoostClassifier
modeloAdaBoost = AdaBoostClassifier(random_state = 0) # elimina randomizacao (sempre mesmo resultado)
resultadoAdaBoost = fit_and_predict("AdaBoostClassifier", modeloAdaBoost, treino_dados, treino_marcacoes, teste_dados, teste_marcacoes)
resultados[resultadoAdaBoost] = modeloAdaBoost

# Algoritmo Um Contra Resto usando LinearSVC
from sklearn.multiclass import OneVsRestClassifier
from sklearn.svm import LinearSVC
modeloOneVsRest = OneVsRestClassifier(LinearSVC(random_state = 0)) # elimina randomizacao (sempre mesmo resultado)
resultadoOneVsRest = fit_and_predict("OneVsRest", modeloOneVsRest, treino_dados, treino_marcacoes, teste_dados, teste_marcacoes)
resultados[resultadoOneVsRest] = modeloOneVsRest

# Algoritmo Um Contra Um (todas as categorias são testadas entre si)
from sklearn.multiclass import OneVsOneClassifier
modeloOneVsOne = OneVsOneClassifier(LinearSVC(random_state = 0))
resultadoOneVsOne = fit_and_predict("OneVsOne", modeloOneVsOne, treino_dados, treino_marcacoes, teste_dados, teste_marcacoes)
resultados[resultadoOneVsOne] = modeloOneVsOne

maximo = max(resultados)
vencedor = resultados[maximo]

print("Vencedor: ")
print(vencedor)

teste_real(vencedor, validacao_dados, validacao_marcacoes)

acerto_base = max(Counter(validacao_marcacoes).values())
taxa_de_acerto_base = 100.0 * acerto_base / len(validacao_marcacoes)
print("Taxa de acerto base: %f" % taxa_de_acerto_base)

total_de_elementos = len(validacao_dados)
print("Total de teste: %d" % total_de_elementos)