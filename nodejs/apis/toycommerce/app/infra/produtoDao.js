function ProdutoDao(connection) {
	this._connection = connection;
}

ProdutoDao.prototype.carregaTodos = function(callback) {
	this._connection.query('select * from Livro', callback);
}

ProdutoDao.prototype.salva = function(produto, callback){
	// o modulo pega as key:value do objeto para definir os campos e valores
	this._connection.query('insert into Livro set ?', produto, callback);
};

module.exports = function() {
	return ProdutoDao;
};