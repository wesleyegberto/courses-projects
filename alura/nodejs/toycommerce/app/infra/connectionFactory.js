var mysql = require('mysql');

var connectDb = function() {
	var database = '';
	
	// através de process.env conseguimos acessar as variáveis de ambiente
	if (!process.env.NODE_ENV || process.env.node === 'dev') {
		database = 'livraria';
	} else if (process.env.NODE_ENV == 'test') {
		database = 'livraria_test';
	}

	return mysql.createConnection({
	    host : 'localhost',
	    user : 'root',
	    password : '1234abc@',
	    database : database
	});
};

// Express load invoca o module.exports e salva o resultado
module.exports = function() {
	// Fazemos o retorno para o Express-Load não abrir a conexão
	return connectDb;
}