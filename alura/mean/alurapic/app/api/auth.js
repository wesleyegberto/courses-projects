const mongoose = require('mongoose');
const jwt = require('jsonwebtoken');
const cookie = require('cookie');

module.exports = function (app) {
	const api = {};
	const collectionUsuarios = mongoose.model('Usuario');

	api.autentica = function (req, resp) {
		console.log('Autenticando: ' + JSON.stringify(req.body));
		let usuario = req.body;

		collectionUsuarios.find({
				login: usuario.login,
				senha: usuario.senha
			})
			.then(usuarioDb => {
				if (!usuarioDb) {
					resp.sendStatus(401).end();
				} else {
					const token = jwt.sign({ login: usuarioDb.login }, app.get('secret'), {
						expiresIn: '1h',
						// algorithm: HS256 // default
					});
					console.log('Token gerado: ' + token);

					// seta o token no Header Set-Cookie (httpOnly não permitirá o Angular acessar o token)
					resp.setHeader('Set-Cookie', cookie.serialize('x-access-token', token, { httpOnly: true }));
					// seta o token no Header
					resp
						.set('x-access-token', token)
						.end();
				}
			}, err => {
				console.log(err);
				resp.sendStatus(401).end();
			});
	};

	api.validaToken = function (req, resp, next) {
		let token = req.cookies['x-access-token'] || req.headers['x-access-token'];

		if (token) {
			console.log('Request com token: ' + token);
			jwt.verify(token, app.get('secret'), function (err, decoded) {
				if (err) {
					console.log('Token invalido');
					resp.sendStatus(401).end();
				} else {
					req.usuario = decoded;
					next(); // chain middlewares
				}
			});
		} else {
			console.log('Request sem token');
			resp.sendStatus(401).end();
		}
	};

	return api;
};