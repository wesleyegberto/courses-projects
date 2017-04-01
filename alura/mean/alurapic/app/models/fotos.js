var mongoose = require('mongoose');

// criando schema para o Mongoose validar
var fotoSchema = mongoose.Schema({
	titulo: {
		type: String,
		required: true
	},
	url: {
		type: String,
		required: true
	},
	descricao: {
		type: String
	},
	grupo: {
		type: Number,
		required: true
	}
});

mongoose.model('Foto', fotoSchema);