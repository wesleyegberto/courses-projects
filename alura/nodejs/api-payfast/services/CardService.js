var restify = require('restify');

var client = restify.createJsonClient({url: 'http://www.mocky.io/v2/58dca60c0f00003116e45905'});

client.post('path', {}, (err, req, resp, result) => {
	console.log(err);
	console.log(result);
});