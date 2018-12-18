const express = require('express');
const consign = require('consign'); // evolução do express-load
const bodyParser = require('body-parser');
const expressValidator = require('express-validator');
const morgan = require('morgan');
const logger = require('../utils/logger.js');

module.exports = function() {
	var app = express();

	// using the commong format from Apache
	app.use(morgan('common', {
		stream: {
			write: function(commonFormattedMessage) {
				// write the formatted message from morgon
				logger.info(commonFormattedMessage);
			}
		}
	}));

	app.use(bodyParser.urlencoded({extended:true}));
	app.use(bodyParser.json());
	app.use(expressValidator());

	consign()
		.include('dao')
		.then('services')
		.then('controllers')
		.into(app);

	return app;
}