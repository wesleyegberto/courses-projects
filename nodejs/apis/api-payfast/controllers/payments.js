const logger = require('../utils/logger.js');

function sendError(response, httpStatus, internalStatus, internalMessage, errorDetails) {
	response.status(status)
	.send(error)
	.end();
}

module.exports = function(app) {
	app.put('/payments/:id', (req, resp) => {
		let connection = app.dao.connectionFactory();
		let paymentDao = new app.dao.paymentDao(connection);
		paymentDao.getById(req.params.id, (err, paymentResult) => {
			if (err) {
				sendError(resp, 500, 2, 'An error occurred during the search', err);
				connection.end();
				return;
			}
			if(paymentResult && paymentResult.length && paymentResult[0].id > 0) {
				let payment = paymentResult[0];
				// call a service
				let cardService = new app.services.cardService();

				cardService.authorize(payment, (errRest, reqRest, respRest, resultRest) => {
					if (err) {
						sendError(resp, 500, 2, 'An error occurred during the validation', err);
					} else {
						console.log(resultRest);
						payment.status = 'PAID';
						paymentDao.update(payment, (err, result) => {
							if (err) {
								sendError(resp, 500, 2, 'An error occurred during the update', err);
							} else {
								resp.status(204).end();
							}
						});
					}
					connection.end();
				});
			} else {
				resp.status(404).end();
				connection.end();
			}
		});
	});

	app.post('/payments', (req, resp) => {
		// validate the body
		req.validate("type", "Type of payment is required").notEmpty();
		req.validate("currency", "Currency is required").notEmpty();
		req.validate("amount", "Amout is required").notEmpty().isFloat();
		req.validate("description", "Description is required").notEmpty();

		// check the errors
		let errors = req.validationErrors();
		if (errors) {
			sendError(resp, 400, 2, 'Invalid data', errors);
			return;
		}

		let payment = req.body;
		let connection = app.dao.connectionFactory();
		let paymentDao = new app.dao.paymentDao(connection);

		payment.status = 'CREATED';
		payment.date = new Date();

		paymentDao.save(payment, (err, result) => {
			if (err) {
				logger.error('Error to store a payment: ' + JSON.stringify(err));
				sendError(resp, 500, 2, 'An error occurred during the insert', err);
			} else {
				resp.status(201)
					.location('/payment/' + result.insertId)
					.end();
			}
			connection.end();
		});
	});

	app.delete('/payments/:id', (req, resp) => {
		let connection = app.dao.connectionFactory();
		let paymentDao = new app.dao.paymentDao(connection);

		paymentDao.deleteById(req.params.id, (err, paymentResult) => {
			if (err) {
				sendError(resp, 500, 2, 'An error occurred during the delete', err);
			} else {
				resp.status(404).end();
			}
			connection.end();
		});
	});

	app.get('/payments/:id', (req, resp) => {
		let connection = app.dao.connectionFactory();
		let paymentDao = new app.dao.paymentDao(connection);
		let cache = app.services.cacheClient();
		let paymentId = req.params.id;

		cache.get('payment-2' + paymentId, (err, cachedPayment) => {
			if(err || !cachedPayment) {
				logger.info('MISS: payment');
				paymentDao.getById(paymentId, (err, paymentResult) => {
					if (err) {
						sendError(resp, 500, 2, 'An error occurred during the search', err);
					} else if(paymentResult && paymentResult.length && paymentResult[0].id > 0) {
						let payment = paymentResult[0];
						cache.set('payment-' + paymentId, payment);
						resp.json(payment);
					} else {
						resp.status(404).end();
					}
					connection.end();
				});
			} else {
				resp.json(cachedPayment);
				connection.end();
			}
		});
	});
}