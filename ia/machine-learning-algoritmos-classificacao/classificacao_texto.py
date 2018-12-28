#!-*- encoding: utf-8 -*-

"""
Código 3.
Código para tentar classificar o conteúdo de e-mails em algumas classificações.

Classificações:
- Comercial: 1
- Técnico: 2
- Carreira: 3

Para analisar os textos é feito processo de limpeza do conteúdo.
- Removido pontuações;
- Removido palavras sem valor para nosso objetivo (pronomes possessivos, artigos, etc);
- Extraído raizes das palavras.

"""

import pandas as pd # Ler o CSV
from collections import Counter
import numpy as np
import nltk

# Treino com k-fold
# from sklearn.cross_validation import cross_val_score # Python 2
from sklearn.model_selection import cross_val_score # Python 3

# Leitura do CSV em um dataframe
csv_df = pd.read_csv('emails_classificar.csv')
# Extração da coluna com os textos dos emails
conteudos_emails = csv_df['email']

# Converte para lowercase e separa as palavras num array
linhas_em_minuculas = conteudos_emails.str.lower()

# nltk.download('punkt') # Baixa ou atualiza
palavras_por_linha = [nltk.tokenize.word_tokenize(linha) for linha in linhas_em_minuculas]

# Dicionario de palavras na ordem de seus ocorrências
dicionario = set()

# Obtem a lista de palavras sem significado para regra de e-mails (pronomes, artigos, etc)
# nltk.download('stopwords') # Baixa ou atualiza as palavras
stopwords = nltk.corpus.stopwords.words('portuguese')

# nltk.download('rslp') # Baixa ou atualiza o módulo de raizes das palavras
# Obtem o removedor de raiz das palavras
stemmer = nltk.stem.RSLPStemmer()

# Adiciona todas as palavras de cada linha no dicionario
for palavras in palavras_por_linha:
    # Filtra as palavras com significado e com tamanho maior 3 (foi escolhido descartar pontos e palavras de 2 chars)
    validas = [stemmer.stem(palavra) for palavra in palavras if palavra not in stopwords and len(palavra) > 2]
    dicionario.update(validas)

qtde_palavras = len(dicionario)

print(dicionario)
print("Qtde palavras no dicionário: %d \n" % qtde_palavras)

# Tuplas de cada palavra com sua posição no dicionario
palavras_indexadas = zip(dicionario, range(qtde_palavras)) # Python 2 usar xrange

# Map/Hash contendo cada palavra e sua posição no dicionário
tabela_traducao_indices = { palavra:indice for palavra,indice in palavras_indexadas }

"""
Função para transformar um array de palavras em um array de ocorrências
onde cada posição do array corresponde a posição daquela palavra no array.
E o valor de cada item do array é a quantidade de ocorrências daquela palavra
no texto dado.
"""
def traduzir_palavras(palavras, tabela_traducao_indices):
    linha_traduzida = [0] * len(tabela_traducao_indices)
    # Incrimenta as ocorrencias de cada palavra na posição do dicionario
    for palavra in palavras:
        if len(palavra) > 0:
            # Extrai a raiz da palavra para verificar se existe no dicionario
            raiz = stemmer.stem(palavra)
            if raiz in tabela_traducao_indices:
                indice = tabela_traducao_indices[raiz] # Posição no dicionário
                linha_traduzida[indice] += 1
    return linha_traduzida

# Traduz as palavras de cada linha em suas ocorrências no dicionário usando a tabela de tradução
linhas_traduzidas = [traduzir_palavras(palavras, tabela_traducao_indices) for palavras in palavras_por_linha]
classificacoes = csv_df['classificacao']

# Converte as listas para arrays manipuláveis pela lib
X = np.array(linhas_traduzidas)
Y = np.array(classificacoes.tolist())

# Separação da massa de treino e validação
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
modeloAdaBoost = AdaBoostClassifier(random_state = 0) # elimina randomizacao (sempre mesmo resultado)
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