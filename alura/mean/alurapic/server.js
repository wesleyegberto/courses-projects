var http = require('http');
var app = require('./config/express');

require('./config/conexaoMongodb')('localhost/alurapic');

http.createServer(app)
	.listen(3000, function() {
		console.log('Server está de pé');
	});