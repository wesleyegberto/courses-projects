angular.module('alurapic')
	.factory('tokenInterceptor', ['$window', '$location', '$q', function($window, $location, $q) {
		var interceptor = {
			request: function(config) {
				// console.log('Interceptando request');
				config.headers = config.headers || {};
				if ($window.sessionStorage.token) {
					config.headers['x-access-token'] = $window.sessionStorage.token;
				}
				return config;
			},
			response: function(response) {
				// console.log('Interceptando response');
				var token = response.headers('x-access-token');
				if (token) {
					$window.sessionStorage.token = token;
				}
				return response;
			},
			responseError: function(rejection) {
				if(rejection && rejection.status == 401) {
					delete $window.sessionStorage.token;
					$location.path('/login');
				}
				return $q.reject(rejection);
			}
		};
		return interceptor;
	}]);