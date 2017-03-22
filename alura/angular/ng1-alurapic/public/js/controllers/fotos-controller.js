angular.module('alurapic').controller('FotosController', ['$scope', 'fotosService', function($scope, fotosService) {
	$scope.filtro = '';
	$scope.fotos = [];

	fotosService.carregaFotos()
		.then(function(fotos) {
			$scope.fotos = fotos;
		})
		.catch(function(retornoAcao) {
			$scope.mensagem = retornoAcao.mensagem;
		});

	$scope.remove = function(foto) {
		if(!foto) return;

		fotosService.apagaFoto(foto)
			.then(function(retornoAcao) {
				var indiceDaFoto = $scope.fotos.indexOf(foto);
      	$scope.fotos.splice(indiceDaFoto, 1);
				$scope.mensagem = retornoAcao.mensagem;
			})
			.catch(function(retornoAcao) {
				$scope.mensagem = retornoAcao.mensagem
			});
	};
}]);