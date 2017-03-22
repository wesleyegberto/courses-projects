class NegociacaoController {
  constructor() {
    // bind associa o contexto de um objeto na função querySelector
    // porque o querySelect usa this para navegar no DOM
    let $ = document.querySelector.bind(document);

    this._inputData = $("#data");
    this._inputQuantidade = $("#quantidade");
    this._inputValor = $("#valor");
  }

  adiciona(event) {
    // cancela o submit
    event.preventDefault();

    // let data = new Date(...this._inputData.value.replace(/-/g, ','))

    // ES2015 - spread operator = cada posição do array vira um argumento
    let data = new Date(... this._inputData.value.split('-').map((val, idx) => { return val - idx % 2}));

    let negociacao = new Negociacao(
      data,
      this._inputQuantidade.value,
      this._inputValor.value
    );
    console.log(negociacao);
  }
}