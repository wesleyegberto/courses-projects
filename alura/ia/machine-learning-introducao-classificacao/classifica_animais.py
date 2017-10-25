#!/usr/bin/python
# Caracteristicas: [Eh gordinho?, Tem perna curta?, Faz Auau?]
# Labels: 1 - Porco, -1 - Cachorro
porco1 = [1, 1, 0]
porco2 = [1, 1, 0]
porco3 = [1, 1, 0]

cachorro1 = [1, 1, 1]
cachorro2 = [0, 1, 1]
cachorro3 = [0, 1, 1]

dados = [porco1, porco2, porco3, cachorro1, cachorro2, cachorro3]
marcacoes = [1, 1, 1, -1, -1, -1]


# Utiliza o algoritmo Multinomial Naive Bytes
from sklearn.naive_bayes import MultinomialNB

# Cria o modelo e o treina (adequa)
modelo = MultinomialNB()
modelo.fit(dados, marcacoes);

# Dados para teste
testes = [
    [1, 1, 1],
    [0, 1, 0],
    [0, 0, 1]
]
esperados = [-1, 1, 1]
total_elementos = len(testes)

resultado = modelo.predict(testes)

# Verifica a diferenca (sera gerado um array com 0 onde for igual ao esperado)
diferencas = resultado - esperados
# Conta os acertos
acertos = [d for d in diferencas if d == 0]
total_acertos = len(acertos)
taxa_acertos = 100.0 * total_acertos / total_elementos

print(resultado)
print(diferencas)
print(taxa_acertos)