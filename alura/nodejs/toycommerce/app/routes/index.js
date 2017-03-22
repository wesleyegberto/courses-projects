module.exports = function(app) {
    app.get("/",function(req, res) {
		var connection = app.infra.connectionFactory();
		var produtoDao = new app.infra.produtoDao(connection);

        produtoDao.carregaTodos(function(error, results, fields) {
            res.render('home/index', { livros: results });
        });
        connection.end();

    });
}