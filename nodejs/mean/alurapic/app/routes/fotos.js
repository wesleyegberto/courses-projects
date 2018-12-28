module.exports = function(app) {
	const api = app.api.fotos;

	app.route('/v1/fotos')
		.get(api.lista)
		.post(api.salva);

	app.route('/v1/fotos/:id')
		.get(api.pesquisaPorId)
		.put(api.atualiza)
		.delete(api.removePorId);
};