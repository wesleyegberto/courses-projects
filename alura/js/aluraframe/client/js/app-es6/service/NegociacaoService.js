class NegociacaoService {

  constructor() {
    this._httpService = new HttpService();
  }

  _importaNegociacoesDe(url) {
    // ES6 - Promise
    return new Promise((resolve, reject) => {
      this._httpService
        .get(url)
        .then(responseJson =>
          resolve(responseJson.map(objeto=> new Negociacao(new Date(objeto.data), objeto.quantidade, objeto.valor)))
        ).catch(err => reject('Não foi possível efetuar importação das negociações.'));
    });
  }

  importaNegociacoesSemanaAtual() {
    return this._importaNegociacoesDe('negociacoes/semana');
  }

  importaNegociacoesSemanaPassada() {
    return this._importaNegociacoesDe('negociacoes/anterior');
  }

  importaNegociacoesSemanaRetrasada() {
    return this._importaNegociacoesDe('negociacoes/retrasada');
  }

  importaTodasNegociacoes(listaAtual) {
    if (!listaAtual) {
      listaAtual = [];
    }
    return new Promise((resolve, reject) => {
      Promise.all([
        this.importaNegociacoesSemanaAtual(),
        this.importaNegociacoesSemanaPassada(),
        this.importaNegociacoesSemanaRetrasada()
      ])
        .then(negociacoes => negociacoes.reduce((arrayAchatado, array) => arrayAchatado.concat(array), []))
        .then(negociacoes => negociacoes.filter(n => !listaAtual.some(nn => n.equalsTo(nn))))
        .then(negociacoes => resolve(negociacoes))
        .catch(erro => reject(erro));
    });
  }

  cadastraUsandoServico(negociacao) {
    new HttpService()
      .post('/negociacoes', negociacao)
      .then(() => 'Negociação enviada com sucesso')
      .catch(erro => `Não foi possível enviar a negociação: $(erro)`);
  }

  cadastra(negociacao) {
    return ConnectionFactory
      .getConnection()
      .then(conexao => new NegociacaoDao(conexao))
      .then(dao => dao.adiciona(negociacao))
      .then(() => 'Negociação adicionada com sucesso')
      .catch(erro => {
        throw new Error("Não foi possível adicionar a negociação: " + erro);
      });
  }

  lista() {
    return ConnectionFactory
      .getConnection()
      .then(connection => new NegociacaoDao(connection))
      .then(dao => dao.listaTodos())
      .catch(erro => {
        console.log(erro);
        throw new Error('Não foi possível obter as negociações');
      });
  }

  apaga() {
    return ConnectionFactory
      .getConnection()
      .then(connection => new NegociacaoDao(connection))
      .then(dao => dao.apagaTodos())
      .catch(erro => {
        console.log(erro);
        throw new Error('Não foi possível apagar as negociações');
      });
  }
}