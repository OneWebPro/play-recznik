class Controller

  scope = undefined

  constructor: ($scope, polishService) ->
    scope = $scope
    scope.polish_hints = []
    scope.polishService = polishService
    scope.$watch 'polish_text' , debounce(@update,500)
    scope.$watch 'polish_hints' , (value) ->
      if(value? and value.length == 1)
        scope.polish_hints = []
    scope.changePolish = (word) ->
      scope.polish_text = word
      scope.polish_hints = []
    scope.translatePolish = ->
      if(scope.polish_text?.length)
        scope.polishService.translate(scope.polish_text).then (results) =>
          scope.polish_results = results
      else
        scope.polish_results = []

  update: (value) ->
    scope.polish_hints = []
    if(value?)
      scope.polishService.typing(scope.polish_text).then (results) =>
        scope.polish_hints = results


angular.module('app').controller 'PolishSearchController', ['$scope','polishService', Controller]