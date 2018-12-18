angular.module('alurapic')
	.controller('GruposController', function($scope, $http) {
		$scope.grupos = [];
		$http.get('/v1/grupos')
		.then(function(grupos) {
			$scope.grupos = grupos.data;
		})
		.catch(function(erro) {
			console.log(erro);
		});
	});