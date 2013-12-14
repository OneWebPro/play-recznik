class Controller

  scope = undefined

  constructor: ($scope, serbianService) ->
    scope = $scope
    scope.serbian_hints = []
    scope.serbian_word = {}
    scope.serbianService = serbianService
    scope.$watch 'serbian_text' , debounce(@update,500)
    scope.$watch 'serbian_hints' , (value) ->
      if(value? and value.length == 1)
        scope.serbian_hints = []
    scope.changeSerbian = (word) ->
      scope.serbian_hints = []
      scope.serbian_text = word
    scope.translateSerbian = ->
      if(scope.serbian_text?.length)
        scope.serbianService.translate(scope.serbian_text).then (results) =>
          scope.serbian_results = results
      else
        scope.serbian_results = []

  update: (value) ->
    scope.serbian_hints = []
    if(value?.length)
      scope.serbianService.typing(scope.serbian_text).then (results) =>
        scope.serbian_hints = results


angular.module('app').controller 'SerbianSearchController', ['$scope','serbianService', Controller]