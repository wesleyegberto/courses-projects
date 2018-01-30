class Negociacao {
	constructor(data, quantidade, valor) {
		if (data) {
			this._data = new Date(data.getTime());
		} else {
			this._data = new Date();
		}
		this._quantidade = quantidade;
		this._valor = valor;

		// Torna este objeto imutável em primeiro nível (mas ainda precisamos
		// tomar cuidado com as referencias que retornamos)
		Object.freeze(this);
		Object.freeze(this._data);
	}

	get volume() {
		return this._quantidade * this._valor;
	}

	get data() {
		// programação defensiva
		return new Date(this._data.getTime());
	}

	get quantidade() {
		return this._quantidade;
	}

	get valor() {
		return this._valor;
	}

	equalsTo(outraNegociacao) {
		return JSON.stringify(this) == JSON.stringify(outraNegociacao);
	}
}