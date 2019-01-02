#!-*- encoding: utf-8 -*-

"""
Treino com visualização dos dados em gráficos.

É utilizado o algoritmo SVM.

É feito reescala dos dados de entrada para não influenciar o algoritmo.

A visualização é feita utilizando modelos onde temos duas variáveis.
Caso tenha mais de duas seria preciso um gráfico 3d ou pareamento das variáveis.
"""

import matplotlib.pyplot as plt # plotar na imagem
import pandas as pd
import numpy as np

uri = "https://gist.githubusercontent.com/guilhermesilveira/1b7d5475863c15f484ac495bd70975cf/raw/16aff7a0aee67e7c100a2a48b676a2d2d142f646/projects.csv"
df_dados = pd.read_csv(uri)

df_dados = df_dados.rename(columns = {
    'expected_hours': 'horas_esperadas',
    'price': 'preco',
    'unfinished': 'nao_finalizado'
})

df_dados['finalizado'] = df_dados['nao_finalizado'].map({
    0: 1,
    1: 0
})


"""
Visualização dos dados usando Seaborn
"""
import seaborn as sns
sns.set(style="whitegrid")



"""
plota um gráfico de dispersão
    @params hue define cor de plotagem variando pela coluna "finalizado"
"""
sns.scatterplot(x="horas_esperadas", y="preco", hue="finalizado", data=df_dados)



"""
plota um gráfico de relação separado os gráficos pela coluna "finalizado"
    @params hue define cor de plotagem variando pela coluna "finalizado"
    @params col define a separação dos gráficos pela coluna "finalizado"
"""
sns.relplot(x="horas_esperadas", y="preco", hue="finalizado", col="finalizado", data=df_dados)


SEED = 20
# se setarmos aqui podemos tirar o random_state das chamadas quando estas usam o np.random
np.random.seed(SEED)

"""
Separação dos dados e treino do modelo.
"""
x = df_dados[["horas_esperadas", "preco"]]
y = df_dados["finalizado"]

# método para separar dados
from sklearn.model_selection import train_test_split
raw_treino_x, raw_teste_x, treino_y, teste_y = train_test_split(x, y, test_size = 0.20, stratify = y)


"""
Trata as features.
"""
from sklearn.preprocessing import StandardScaler
scaler = StandardScaler()
scaler.fit(raw_treino_x) # treina para encontrar a escala ideal
# a transformação recebe um dataframe do pandas e retorna um array do numpy (atentar nas operacoes)
treino_x = scaler.transform(raw_treino_x)
teste_x = scaler.transform(raw_teste_x)


"""
Algoritmo Baseline (chuta tudo 1)
"""
# Efetua as previsões usando algoritmo de baseline
from sklearn.metrics import accuracy_score
baseline_acuracia = accuracy_score(teste_y, np.ones(len(teste_y))) * 100
print("A acurácia do algoritmo de baseline foi %.2f%%" % baseline_acuracia)


# Criação e treino do modelo
from sklearn.svm import SVC
modelo = SVC()
modelo.fit(treino_x, treino_y)
previsoes = modelo.predict(teste_x)

# Calcula a acurácia do modelo
acuracia = accuracy_score(teste_y, previsoes) * 100
print("A acurácia foi %.2f%%" % acuracia)



"""
Plotando o gráfico dos dados de testes e a borda de decisão do modelo treinado.
"""
# Extrai os valores da coluna da matriz (pois o scaler recebeu o dataframe e retornou um numpy.array)
horas_esperadas = teste_x[:,0]
preco = teste_x[:,1]


# Montagem da matriz para desenhar a borda de decisão
x_min = horas_esperadas.min()
x_max = horas_esperadas.max()
y_min = preco.min()
y_max = preco.max()

# gera os intervalos em cada eixo
eixo_x = np.arange(x_min, x_max, (x_max - x_min) / 100)
eixo_y = np.arange(y_min, y_max, (y_max - y_min) / 100)

# gera os intervalos de um eixo no outro eixo
xx, yy = np.meshgrid(eixo_x, eixo_y)
# gera a listsa com todos os pontos do grid (pares de x e y)
pontos = np.c_[xx.ravel(), yy.ravel()]

Z = modelo.predict(pontos)
# transforma a lista num grid
Z = Z.reshape(xx.shape)

# configura a imagem
fig, ax = plt.subplots()

# configura a plotagem da borda de decisão
plt.contourf(xx, yy, Z, alpha=0.3)

# plota os dados de testes
plt.scatter(horas_esperadas, preco, c=teste_y, s=1)

# Exibe os gráficos
plt.show()

