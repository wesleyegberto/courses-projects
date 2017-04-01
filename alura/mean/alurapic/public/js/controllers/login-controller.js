angular.module('alurapic')
	.controller('LoginController', ['$scope', '$http', '$location', function($scope, $http, $location) {
	  $scope.mensagem = '';
	  $scope.usuario = {};

	  $scope.autentica = function() {
	  	$http.post('/autenticar', {
		  		login: $scope.usuario.login,
		  		senha: $scope.usuario.senha
		  	})
		  	.then(function() {
		  		$location.path('/');
		  	})
		  	.catch(function(err) {
		  		console.log(err);
		  		$scope.mensagem = 'Ocorreu um erro durante a autenticação.';
		  	})
	  };
	}]);