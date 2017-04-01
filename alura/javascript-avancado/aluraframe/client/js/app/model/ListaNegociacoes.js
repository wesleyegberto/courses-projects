class ListaNegociacoes {

	constructor() {
		this._negociacoes = []

    // Reflect.apply(<function>, <context>, [<args>])
    // Reflect.apply(this._armadilha, this._contexto, [this]);
    // Podemos usar tmb
    // this._armadilha.apply(this._contexto, [this]);
	}

	adiciona(negociacao) {
		// this._negociacoes.push(negociacao);
		// para disparar o proxy
		this._negociacoes = [].concat(this._negociacoes, negociacao);
	}

	get negociacoes() {
		// programação defensiva
		return [].concat(this._negociacoes);
	}

	esvazia() {
		this._negociacoes = [];
	}
}