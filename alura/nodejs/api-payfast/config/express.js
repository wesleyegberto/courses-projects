var express = require('express');
var consign = require('consign'); // evolução do express-load
var bodyParser = require('body-parser');
var expressValidator = require('express-validator');

module.exports = function() {
	var app = express();

	app.use(bodyParser.urlencoded({extended:true}));
	app.use(bodyParser.json());
	app.use(expressValidator());

	consign()
		.include('./dao/connectionFactory.js')
		.then('./dao/paymentDao.js')
		.then('./controllers/payments.js')
		.into(app);

	return app;
}