#!-*- encoding: utf-8 -*-

"""
Código 2.

Cenário igual ao código 1, porém o treino é feito utilizando a técnica do k-fold.
Esta técnica consiste em treinar o modelo usando a permutação das partes dos dados de
treinamento que são divididos em k partes, uma parte delas será usada para teste e o
restante para treino. Após este primeiro
round é feito outro treino utilizando outra parte.

Ex.: Massa com 3 partes: A, B e C.
Primeiro round: Treino com A e B, então testa com C.
Segunda round: Treino com A e C, então testa com B.
Terceiro round: Treino com B e C, então testa com A.
"""

import pandas as pd
from collections import Counter

import numpy as np
# from sklearn.cross_validation import cross_val_score # Python 2
from sklearn.model_selection import cross_val_score # Python 3

df = pd.read_csv('situacao_cliente.csv')
X_df = df[['recencia','frequencia', 'semanas_de_inscricao']]
Y_df = df['situacao']

Xdummies_df = pd.get_dummies(X_df)
Ydummies_df = Y_df

X = Xdummies_df.values
Y = Ydummies_df.values

porcentagem_de_treino = 0.8

tamanho_de_treino = int(porcentagem_de_treino * len(Y))
tamanho_de_validacao = len(Y) - tamanho_de_treino

treino_dados = X[:tamanho_de_treino]
treino_marcacoes = Y[:tamanho_de_treino]

validacao_dados = X[tamanho_de_treino:]
validacao_marcacoes = Y[tamanho_de_treino:]


"""
Efetua o treinamento e teste do modelo usando o K-Fold.
"""
def fit_and_predict(nome, modelo, treino_dados, treino_marcacoes):
    k = 10 # qtde de divisões
    scores = cross_val_score(modelo, treino_dados, treino_marcacoes, cv = k)
    media_taxa_acerto = np.mean(scores)
    print("Média de acerto do algoritmo {0}: {1}".format(nome, media_taxa_acerto))
    return media_taxa_acerto

# Valida o modelo
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
resultadoMultinomial = fit_and_predict("MultinomialNB", modeloMultinomial, treino_dados, treino_marcacoes)
resultados[resultadoMultinomial] = modeloMultinomial

from sklearn.ensemble import AdaBoostClassifier
modeloAdaBoost = AdaBoostClassifier(random_state = 0)
resultadoAdaBoost = fit_and_predict("AdaBoostClassifier", modeloAdaBoost, treino_dados, treino_marcacoes)
resultados[resultadoAdaBoost] = modeloAdaBoost

# Algoritmo Um Contra Resto usando LinearSVC
from sklearn.multiclass import OneVsRestClassifier
from sklearn.svm import LinearSVC
modeloOneVsRest = OneVsRestClassifier(LinearSVC(random_state = 0)) # elimina randomizacao (sempre mesmo resultado)
resultadoOneVsRest = fit_and_predict("OneVsRest", modeloOneVsRest, treino_dados, treino_marcacoes)
resultados[resultadoOneVsRest] = modeloOneVsRest

# Algoritmo Um Contra Um (todas as categorias são testadas entre si)
from sklearn.multiclass import OneVsOneClassifier
modeloOneVsOne = OneVsOneClassifier(LinearSVC(random_state = 0))
resultadoOneVsOne = fit_and_predict("OneVsOne", modeloOneVsOne, treino_dados, treino_marcacoes)
resultados[resultadoOneVsOne] = modeloOneVsOne

maximo = max(resultados)
vencedor = resultados[maximo]

print("Vencedor: ")
print(vencedor)

# Treina o modelo vencedor
vencedor.fit(treino_dados, treino_marcacoes)

teste_real(vencedor, validacao_dados, validacao_marcacoes)

acerto_base = max(Counter(validacao_marcacoes).values())
taxa_de_acerto_base = 100.0 * acerto_base / len(validacao_marcacoes)
print("Taxa de acerto base: %f" % taxa_de_acerto_base)

total_de_elementos = len(validacao_dados)
print("Total de teste: %d" % total_de_elementos)