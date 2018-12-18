function PaymentDao(connection) {
	this._connection = connection;
}

PaymentDao.prototype.fetchAll = function(callback) {
	this._connection.query('select * from Payment', callback);
}

PaymentDao.prototype.getById = function(id, callback) {
	this._connection.query('select * from Payment where id = ?', id, callback);
}

PaymentDao.prototype.save = function(payment, callback){
	this._connection.query('insert into Payment set ?', payment, callback);
};

PaymentDao.prototype.update = function(payment, callback){
	this._connection.query('update Payment set status = ? where id = ?', [payment.status, payment.id], callback);
};

PaymentDao.prototype.deleteById = function(id, callback) {
	this._connection.query('delete from Payment where id = ?', id, callback);
}


module.exports = function() {
	return PaymentDao;
};