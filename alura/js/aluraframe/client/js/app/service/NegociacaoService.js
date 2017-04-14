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
          resolve(responseJson.map(objeto=>
            new Negociacao(new Date(objeto.data), objeto.quantidade, objeto.valor))
          )
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
}