angular.module('alurapic', ['diretivas', 'alurapicServices', 'ngRoute', 'ngResource', 'ngAnimate'])
	.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {

		// Quando o browser e o backend suportam HTML5 mode
		$locationProvider.html5Mode(true);

		$routeProvider
			.when('/fotos/edit/:fotoId', {
				templateUrl: 'partials/foto.html',
				controller: 'FotoController'
			})
			.when('/fotos/new', {
				templateUrl: 'partials/foto.html',
				controller: 'FotoController'
			})
			.when('/fotos', {
				templateUrl: 'partials/fotos.html',
				controller: 'FotosController'
			})
			.otherwise({ redirectTo: '/fotos' })
	}]);