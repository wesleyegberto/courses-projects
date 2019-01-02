import pandas as pd

uri = "https://gist.githubusercontent.com/guilhermesilveira/2d2efa37d66b6c84a722ea627a897ced/raw/10968b997d885cbded1c92938c7a9912ba41c615/tracking.csv"
df = pd.read_csv(uri)

colunas = {
    "home": "principal",
    "how_it_works": "como_funciona",
    "contact": "contato",
    "bought": "comprou"
}

df = df.rename(columns = colunas)

features = df[["principal", "como_funciona", "contato"]]
labels = df["comprou"]

tamanho_treino = int(len(features) * 0.8)

treino_x = features[:tamanho_treino]
treino_y = labels[:tamanho_treino]

teste_x = features[tamanho_treino:]
teste_y = labels[tamanho_treino:]

from sklearn.svm import LinearSVC
modelo = LinearSVC(random_state = 0)
modelo.fit(treino_x, treino_y)

previsoes = modelo.predict(teste_x)

from sklearn.metrics import accuracy_score
acuracia = accuracy_score(teste_y, previsoes) * 100

print("Acurácia: %.2f%%" % acuracia)