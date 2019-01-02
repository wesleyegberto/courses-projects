#!-*- encoding: utf-8 -*-
from sklearn.model_selection import train_test_split
from sklearn.metrics import accuracy_score

import numpy as np
SEED = 5
np.random.seed(SEED)

import pandas as pd
uri = 'https://gist.githubusercontent.com/guilhermesilveira/4d1d4a16ccbf6ea4e0a64a38a24ec884/raw/afd05cb0c796d18f3f5a6537053ded308ba94bf7/car-prices.csv'
dados = pd.read_csv(uri)


"""
Tratamento dos dados
"""
dados = dados.rename(columns={
    'mileage_per_year' : 'milhas_por_ano',
    'model_year' : 'ano_do_modelo',
    'price' : 'preco',
    'sold' : 'vendido'
})

dados.vendido = dados.vendido.map({
    'no' : 0,
    'yes' : 1
})

from datetime import datetime

ano_atual = datetime.today().year
dados['idade_do_modelo'] = ano_atual - dados.ano_do_modelo
dados['km_por_ano'] = dados.milhas_por_ano * 1.60934

dados = dados.drop(columns = ["Unnamed: 0", "milhas_por_ano","ano_do_modelo"], axis=1) # remove as colunas (axis, caso contraio ia remover apenas de linha)


x = dados[["preco", "idade_do_modelo","km_por_ano"]]
y = dados["vendido"]


"""
Aplica modelos dummies para termos um baseline
"""
treino_x, teste_x, treino_y, teste_y = train_test_split(x, y, test_size = 0.25, stratify = y)
print("Treinaremos com %d elementos e testaremos com %d elementos" % (len(treino_x), len(teste_x)))

from sklearn.dummy import DummyClassifier

# Chuta as classes mesma proporção encontradas no treino
dummy_stratified = DummyClassifier(strategy="stratified")
dummy_stratified.fit(treino_x, treino_y)
acuracia = dummy_stratified.score(teste_x, teste_y) * 100
print("A acurácia do dummy stratified foi %.2f%%" % acuracia)

# Chuta a classe mais frequente encontrada no treino
dummy_mostfrequent = DummyClassifier(strategy="most_frequent")
dummy_mostfrequent.fit(treino_x, treino_y)
acuracia = dummy_mostfrequent.score(teste_x, teste_y) * 100
print("A acurácia do dummy mostfrequent foi %.2f%%" % acuracia)



"""
Aplica o modelo LinearSVC
"""
from sklearn.svm import LinearSVC

treino_x, teste_x, treino_y, teste_y = train_test_split(x, y, test_size = 0.25, stratify = y)
print("Treinaremos com %d elementos e testaremos com %d elementos" % (len(treino_x), len(teste_x)))

modelo = LinearSVC()
modelo.fit(treino_x, treino_y)
previsoes = modelo.predict(teste_x)

acuracia = accuracy_score(teste_y, previsoes) * 100
print("A acurácia do LinearSVC foi %.2f%%" % acuracia)



"""
Reescala os dados e aplica modelo SVC
"""
from sklearn.preprocessing import StandardScaler
from sklearn.svm import SVC

raw_treino_x, raw_teste_x, treino_y, teste_y = train_test_split(x, y, test_size = 0.25, stratify = y)
print("Treinaremos com %d elementos e testaremos com %d elementos" % (len(treino_x), len(teste_x)))

scaler = StandardScaler()
scaler.fit(raw_treino_x)
treino_x = scaler.transform(raw_treino_x)
teste_x = scaler.transform(raw_teste_x)

modelo = SVC()
modelo.fit(treino_x, treino_y)
previsoes = modelo.predict(teste_x)

acuracia = accuracy_score(teste_y, previsoes) * 100
print("A acurácia do SVC foi %.2f%%" % acuracia)



"""
Aplica árvore de decisão utilizando o Gini
"""
from sklearn.preprocessing import StandardScaler
from sklearn.tree import DecisionTreeClassifier

treino_x, teste_x, treino_y, teste_y = train_test_split(x, y, test_size = 0.25, stratify = y)
print("Treinaremos com %d elementos e testaremos com %d elementos" % (len(treino_x), len(teste_x)))

modelo = DecisionTreeClassifier(max_depth=5)
modelo.fit(treino_x, treino_y)
previsoes = modelo.predict(teste_x)

acuracia = accuracy_score(teste_y, previsoes) * 100
print("A acurácia da árvore de decisão foi %.2f%%" % acuracia)

"""
Gera o grafo da árvore de decisão
"""
from sklearn.tree import export_graphviz
import graphviz

# gera o grafo a partir do modelo
dot_data = export_graphviz(modelo, out_file=None,
                           filled=True, rounded=True, # formatacao das cores e forma
                           feature_names = x.columns, # nome das features (para não exibir X[0])
                           class_names = ["não", "sim"])
# gera a visualização
grafico_arvore_decisao = graphviz.Source(dot_data)
grafico_arvore_decisao.render('arvore-decisao-grafo.gv', view=True)

