var express = require('express');
var load = require('express-load');
var bodyParser = require('body-parser');
var expressValidator = require('express-validator');

// Exporta o objeto para ser retornado pelo require
module.exports = function() {
	var app = express();

	// Configura a engine renderizadora no express
	app.set('view engine', 'ejs');
	// Seta o caminho padrão das views
	app.set('views', './app/views');


	// Middlewares: modulos que operam no request/response
	// a ordem importa

	// define a pasta com arquivos estáticos
	app.use(express.static('./app/public'));
	// ativamos o middleware do Body-Parser para efetuar a transformação
	// do post do form (URL encoded) para objeto JSON
	app.use(bodyParser.urlencoded({extended: true}));
	app.use(bodyParser.json());
	// ativa o express validator nos requests
	app.use(expressValidator());

	load('infra', {cwd: 'app'})
				.then('routes')
				.into(app);

	// Custom middleware
	app.use(function(req, res, next){
		res.status(404).render("errors/404");
	});

	app.use(function(error,req, res, next){
		if(process.env.NODE_ENV == 'production') {
			res.status(500).render('errors/500');
			return;
		}
		next(errors);
	});

	return app;
}