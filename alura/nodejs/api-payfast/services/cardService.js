const restify = require('restify');

function CardService() {
	this._client = restify.createJsonClient({url: 'http://www.mocky.io/v2'});
}

CardService.prototype.authorize = function(card, callback) {
	this._client.get('/58dca60c0f00003116e45905', callback);
}

module.exports = function() {
	return CardService;
}