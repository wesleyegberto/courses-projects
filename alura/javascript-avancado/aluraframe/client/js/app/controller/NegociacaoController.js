class NegociacaoController {
  constructor() {
    // bind associa o contexto de um objeto na função querySelector
    // porque o querySelect usa this para navegar no DOM
    const $ = document.querySelector.bind(document);

    this._inputData = $("#data");
    this._inputQuantidade = $("#quantidade");
    this._inputValor = $("#valor");

    // this._mensagem = new Mensagem();
    // this._mensagemView = new MensagemView($('#mensagemView'));
    // this._mensagem = ProxyFactory.create(new Mensagem(), ['texto'], model => this._mensagemView.update(model));
    // this._mensagemView.update(this._mensagem);

    // criamos um bind que utiliza um proxy usando o objeto mensagem e atualiza a view automaticamente
    this._mensagem = new Bind(new Mensagem(), new MensagemView($('#mensagemView')), 'texto');

    // this._negociacoesView = new NegociacoesView($('#negociacoesView'));
    // this._listaNegociacoes = ProxyFactory.create(new ListaNegociacoes(), ['adiciona', 'esvazia'], model => this._negociacoesView.update(model));
    // this._negociacoesView.update(this._listaNegociacoes);

    this._listaNegociacoes = new Bind(new ListaNegociacoes(), new NegociacoesView($('#negociacoesView')), 'adiciona', 'esvazia');
  }

  adiciona(event) {
    // cancela o submit
    event.preventDefault();

    this._listaNegociacoes.adiciona(this._criaNegociacao());
    // agora usamos o bind do model
    // this._negociacoesView.update(this._listaNegociacoes);
    this._limpaFormulario();
  }

  _criaNegociacao() {
    // let data = new Date(...this._inputData.value.replace(/-/g, ','))
    // ES6 - spread operator = cada posição do array vira um argumento
    // let data = new Date(... this._inputData.value.split('-').map((val, idx) => { return val - idx % 2}));
    return new Negociacao(
      DateHelper.textoParaData(this._inputData.value), // ES6 - métodos estáticos
      this._inputQuantidade.value,
      this._inputValor.value
    );
  }

  apaga() {
    this._listaNegociacoes.esvazia();
    // this._negociacoesView.update(this._listaNegociacoes);

    this._mensagem.texto = 'Negociações apagadas com sucesso';
    // agora usamos bind do model
    // this._mensagemView.update(this._mensagem);
  }

  _limpaFormulario() {
    this._inputData.value = '';
    this._inputQuantidade.value = 1;
    this._inputValor.value = 0.0
    this._inputData.focus();
  }
}