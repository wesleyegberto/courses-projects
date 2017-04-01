module.exports = function(app) {
	const api = app.api.auth;

	app.post('/autenticar', api.autentica);
	// protege todas as URLs criando um middleware
	app.use('/*', api.validaToken);
};