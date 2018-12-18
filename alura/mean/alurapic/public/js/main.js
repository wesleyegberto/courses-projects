angular.module('alurapic', ['minhasDiretivas','ngAnimate', 'ngRoute', 'ngCookies', 'ngResource', 'meusServicos'])
	.config(function($routeProvider, $locationProvider, $httpProvider) {
		// ativa o interceptador para manipular o token
		$httpProvider.interceptors.push('tokenInterceptor');

		$routeProvider.when('/login', {
			templateUrl: 'partials/login.html',
			controller: 'LoginController'
		})
		.when('/fotos/edit/:fotoId', {
			templateUrl: 'partials/foto.html',
			controller: 'FotoController'
		})
		.when('/fotos/new', {
			templateUrl: 'partials/foto.html',
			controller: 'FotoController'
		})
		.when('/fotos', {
			templateUrl: 'partials/principal.html',
			controller: 'FotosController'
		})
		.otherwise({redirectTo: '/fotos'});

	});