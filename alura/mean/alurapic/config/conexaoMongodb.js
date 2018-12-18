const mongoose = require('mongoose');


module.exports = function(url) {
	// se não tiver o database então é criado
	mongoose.connect(`mongodb://${url}`);

	// eventos
	mongoose.connection.on('connected', () => console.log('Conectado ao MongoDB'));
	mongoose.connection.on('disconnected', () => console.log('Desconectado do MongoDB'));
	// evento de erro (para evitar que feche a aplicação)
	mongoose.connection.on('error', (err) => {
		console.log('MongoDB error: ' + JSON.stringify(err));
	});

	// Registra um listener no Node para fechar as conexão antes de fechar a aplicação
	process.on('SIGINT', () => {
		console.log('Aplicação está sendo finalizada...');
		mongoose.connection.close(function() {
			console.log('Conexões finalizadas!');
		});
	});
}