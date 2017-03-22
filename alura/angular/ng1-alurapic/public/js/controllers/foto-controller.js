angular.module('alurapic').controller('FotoController', ['$scope', '$routeParams', 'fotosService', function($scope, $routeParams, fotosService) {
	$scope.foto = {};

	
	if($routeParams.fotoId) {
		fotosService.carregaFoto($routeParams.fotoId)
			.then(function(foto) {
				$scope.foto = foto;
			})
			.catch(function(retornoAcao) {
				$scope.mensagem = retornoAcao.mensagem;
			});
	}

	$scope.salvarFoto = function() {
		if(!$scope.formulario.$valid) {
			$scope.mensagem = 'Alguns campos estão inválidos, por favor, verifique.';
			return;
		}

		fotosService.salvaFoto($scope.foto)
			.then(function(retornoAcao) {
		  	$scope.mensagem = retornoAcao.mensagem;
		  	if(retornoAcao.inclusao) {
		  		$scope.foto = {};
		  	}
		  	// dispara o evento para os listeners
		  	$scope.$broadcast('fotoSalva');
			})
			.catch(function(retornoAcao) {
				$scope.mensagem = retornoAcao.mensagem;
			});
	};
}]);