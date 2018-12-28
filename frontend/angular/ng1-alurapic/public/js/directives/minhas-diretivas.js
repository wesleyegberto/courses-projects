// Declaramos por formalidade pois o modulo alurapic já tem e nesse caso injeta
angular.module('diretivas', ['alurapicServices'])
	.directive('meuPainel', function() {
		// Directive Definition Object
	  return {
	    restrict: 'AE',
	    scope: {
	    	titulo: '@' // copia de valor
	    },
	    transclude: true,
	    templateUrl: 'js/directives/meu-painel.html'
	  };
	})
	.directive('minhaFoto', function() {
		return {
			restrict: 'E',
			scope: {
				titulo: '@',
				urlImagem: '@urlImagem'
			},
			transclude: false,
			template: '<img class="img-responsive center-block" ng-src="{{urlImagem}}" alt="{{titulo}}">'
		};
	})
	.directive('meuBotaoPerigo', function() {
		return {
			restrict: 'E',
			scope: {
		    nome: '@',
		    acao : '&'
			},
			template: '<button class="btn btn-danger btn-block" ng-click="acao()">{{nome}}</button>'
		};
	})
	.directive('meuFoco', function() {
		return {
			restrict: 'A',
			scope: {
				evento: "@"
			},
			link: function(scope, element) {
				/* usando observador em cima de um atributo pode ser custoso
				scope.$watch('focado', function(newValue, oldValue) {
        	alert('mudei');
        });*/
        scope.$on(scope.evento, function() {
					element[0].focus();
				});
			}
		};
	})
	.directive('meusTitulos', function() {
		return {
			restrict: 'E',
			template: '<ul><li ng-repeat="titulo in titulos">{{titulo}}</li></ul>',
			controller: ['$scope', 'fotosService', function($scope, fotosService) {
				fotosService.carregaFotos()
					.then(function(fotos) {
						$scope.titulos = fotos.map(function(foto) {
							return foto.titulo;
						});    
	        });
      }]
		};
	});;