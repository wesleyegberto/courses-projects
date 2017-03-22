angular.module('alurapicServices', ['ngResource'])
	.factory('fotosResource', ['$resource', function($resource) {
		return $resource('v1/fotos/:fotoId', null, {
			update: { method: 'PUT' }
		});
	}])
	.factory('fotosService', ['fotosResource', '$q', function(fotosResource, $q) {
		return {
			salvaFoto: function(foto) {
				return $q(function(resolve, reject) {
					if(foto._id) {
						fotosResource.update({fotoId: foto._id}, foto,
							function() {
								resolve({
									inclusao: false,
									mensagem: 'Foto atualizada com sucesso.'
								});
							}, function(erro) {
								console.log(erro);
								reject({
									mensagem: 'Não foi possível atualizar a foto.'
								});
							});
					} else {
						fotosResource.save(foto,
							function() {
								resolve({
									inclusao: true,
									mensagem: 'Foto inserida com sucesso.'
								});
							}, function(erro) {
								console.log(erro);
								reject({
									mensagem: 'Não foi possível inserir a foto.'
								});
							});
					}
				});
			},
			apagaFoto: function(foto) {
				return $q(function(resolve, reject) {
					fotosResource.delete({fotoId: foto._id},
						function() {
							resolve({
								mensagem: 'Foto "' + foto.titulo + '" removida com sucesso.'
							});
						}, function(err) {
							console.log(err);
							reject({
								mensagem: 'Não foi possível remover a foto "' + foto.titulo + '".'
							});
						});
				});
			},
			carregaFotos: function() {
				return $q(function(resolve, reject) {
					fotosResource.query(resolve,
						function(err) {
							console.log(err);
							reject({
								mensagem: 'Ocorreu um erro ao carregar as fotos.'
							});
						});
				});
			},
			carregaFoto: function(id) {
				return $q(function(resolve, reject) {
					fotosResource.get({fotoId: id},
						resolve,
						function(err) {
							console.log(err);
							reject({
								mensagem: 'Ocorreu um erro ao carregar as fotos.'
							});
						});
				});
			}
		};
	}]);