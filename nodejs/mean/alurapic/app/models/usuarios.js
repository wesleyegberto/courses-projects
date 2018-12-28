var mongoose = require('mongoose');

// criando schema para o Mongoose validar
var usuarioSchema = mongoose.Schema({
	login: {
		type: String,
		required: true
	},
	senha: {
		type: String,
		required: true
	}
});

mongoose.model('Usuario', usuarioSchema);