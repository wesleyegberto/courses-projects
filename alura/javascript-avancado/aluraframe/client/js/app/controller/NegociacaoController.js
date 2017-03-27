class NegociacaoController {
  constructor() {
    // bind associa o contexto de um objeto na função querySelector
    // porque o querySelect usa this para navegar no DOM
    let $ = document.querySelector.bind(document);

    this._inputData = $("#data");
    this._inputQuantidade = $("#quantidade");
    this._inputValor = $("#valor");

    this._mensagem = new Mensagem();
    this._mensagemView = new MensagemView($('#mensagemView'));
    this._mensagemView.update(this._mensagem);

    this._negociacoesView = new NegociacoesView($('#negociacoesView'));
    this._listaNegociacoes = new ListaNegociacoes();
    this._negociacoesView.update(this._listaNegociacoes);
  }

  adiciona(event) {
    // cancela o submit
    event.preventDefault();

    this._listaNegociacoes.adiciona(this._criaNegociacao());

    this._negociacoesView.update(this._listaNegociacoes);
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

  _limpaFormulario() {
    this._inputData.value = '';
    this._inputQuantidade.value = 1;
    this._inputValor.value = 0.0
    this._inputData.focus();
  }
}