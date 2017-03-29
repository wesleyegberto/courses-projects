var mysql = require('mysql');

var connectDb = function() {
	return mysql.createConnection({
	    host : 'localhost',
	    user : 'root',
	    password : '1234abc@',
	    database : 'contas'
	});
};

module.exports = function() {
	return connectDb;
}