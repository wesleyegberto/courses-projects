module.exports = function(app) {
	const api = app.api.grupos;

	app.get('/v1/grupos', api.lista);
};