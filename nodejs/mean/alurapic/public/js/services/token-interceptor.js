angular.module('alurapic')
	.factory('tokenInterceptor', ['$window', '$location', '$cookies', '$q', function($window, $location, $cookies, $q) {
		const USE_COOKIES = true;
		
		var interceptor = {
			request: function(config) {
				// console.log('Interceptando request');
				config.headers = config.headers || {};
				if (USE_COOKIES) {
					console.log($cookies.get('x-access-token'));
					// enviado automaticamente pelo browser no header
					// Cookie de cada request para o mesmo domain
					if($cookies.get('x-access-token')) {
						// mas também podemos reusá-lo em outro header
						config.headers['x-access-token'] = $cookies.get('x-access-token');
					}
				}
				// só enviado nas chamadas do Angular (inclusão manual pelo JS)
				if ($window.sessionStorage.token) {
					config.headers['x-access-token'] = $window.sessionStorage.token;
				} else if ($window.localStorage.token) {
					config.headers['x-access-token'] = $window.localStorage.token;
				}
				return config;
			},
			response: function(response) {
				// console.log('Interceptando response');
				var token = response.headers('x-access-token');
				if (token) {
					if (USE_COOKIES) {
						/**
						 * Cookies são mais vulneráveis à CSRF.
						 * Quando Set-Cookie contém a flag HttpOnly não
						 * pode ser acessado via JS.
						 */
						$cookies.put('x-access-token', token);
					}
					/**
					 * esses storages podem ser acessars via JS do
					 * mesmo domain (por isso são vulneráveis as XSS)
					 */
					// reseta quando o navegador é fechado
					$window.sessionStorage.token = token;
					// persiste mesmo quando fechamos o browser
					$window.localStorage.token = token;
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
