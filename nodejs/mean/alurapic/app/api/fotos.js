const mongoose = require('mongoose');

const app = {};

const collectionFotos = mongoose.model('Foto');

app.lista = function(req, resp) {
  collectionFotos.find({})
  	.then(fotos => {
  		resp.json(fotos);
  	}, error => {
  		console.log(err);
  		resp.status(500).json(err);
  	});
};

app.pesquisaPorId = function(req, resp) {
	collectionFotos.findById(req.params.id)
		.then(foto => {
			if(!foto)
				resp.status(404).end();
			else
				resp.json(foto);
		}, err => {
  		console.log(err);
  		resp.status(500).json(err);
		});
};

app.removePorId = function(req, resp) {
	collectionFotos.remove({'_id': req.params.id})
		.then(() => {
			resp.status(204).end();
		}, err => {
  		console.log(err);
  		resp.status(500).json(err);
		});
};

app.salva = function(req, resp) {
	collectionFotos.create(req.body)
		.then(foto => {
			resp.json(foto);
		}, err => {
  		console.log(err);
  		resp.status(500).json(err);
		});
};

app.atualiza = function(req, resp) {
	collectionFotos.findByIdAndUpdate(req.params.id, req.body)
		.then(foto => {
			resp.status(204).end();
		}, err => {
  		console.log(err);
  		resp.status(500).json(err);
		});
};

module.exports = app;