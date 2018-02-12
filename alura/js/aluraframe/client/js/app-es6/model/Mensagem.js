export class Mensagem {
  constructor(texto) {
    // Edge 13 não suporta params opcionais
    this._texto = texto || '';
  }

  get texto() {
    return this._texto;
  }

  set texto(texto) {
    this._texto = texto;
  }
}