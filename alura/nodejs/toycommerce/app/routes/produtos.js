// Agora usamos a var disponibilizada pelo Express-Load
// var connectionFactory = require('../infra/connectionFactory');

module.exports = function(app) {
	// Requisições para /produto
	app.get('/produtos', (req, resp, next) => {
		var connection = app.infra.connectionFactory();
		var produtoDao = new app.infra.produtoDao(connection);

    produtoDao.carregaTodos(function(err, results){
    	if(err) {
        return next(err);
    	}
    	resp.format({
    		html: () => resp.render('produtos/lista', { listaProdutos: results }),
				json: () => resp.json(results)
			});
    });

    connection.end();
	});

	app.get('/produtos/form', (req, resp) => {
		resp.render('produtos/form', { produto: {}, errosValidacao: {} });
	});

	app.post('/produtos', (req, resp) => {
		// o Body-Parser efetua extração dos dados do post do form
		var produto = req.body;
		// console.log(produto);

		// Cria os validators e chama os assert/validate
		req.assert('titulo', 'Título é obrigatório').notEmpty();
		req.validate('preco', 'Preço inválido').isFloat();

		var errors = req.validationErrors();

		if(errors) {
			resp.format({
				html: () => resp.status(400).render('produtos/form', { produto: produto, errosValidacao: errors }),
				json: () => resp.status(400).json(errors)
			});
			return;
		}


		var connection = app.infra.connectionFactory();
		var produtoDao = new app.infra.produtoDao(connection);

		produtoDao.salva(produto, (err, result) => {
			resp.redirect('/produtos')
		});
	});
};