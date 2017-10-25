import csv

def carregar_acessos():
    X = []
    Y = []

    arquivo = open('acessos.csv', 'rb')
    leitor = csv.reader(arquivo)
    # Pula o header
    leitor.next()
    for home, como_funciona, contato, comprou in leitor:
        dado = [int(home), int(como_funciona), int(contato)]
        X.append(dado)
        Y.append(int(comprou))
    return X, Y

# Carrega os dados para treino
X, Y = carregar_acessos()
# Divide os dados para treino e teste
treino_dados = X[:90]
treino_marcacoes = Y[:90]

teste_dados = X[-9:]
teste_marcacoes = Y[-9:]

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
