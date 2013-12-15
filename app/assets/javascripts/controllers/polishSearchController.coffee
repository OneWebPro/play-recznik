class Controller

  scope = undefined

  constructor: ($scope, polishService) ->
    scope = $scope
    scope.polish_hints = []
    scope.polish_word = {}
    scope.polishService = polishService
    scope.$watch 'polish_text', debounce(@update, 500)
    scope.changePolish = (word) ->
      scope.polish_hints = []
      scope.polish_text = word
    scope.translatePolish = ->
      if(scope.polish_text?.length)
        scope.polishService.translate(scope.polish_text).then (results) =>
          scope.polish_results = results
      else
        scope.polish_results = []
    scope.$on 'translatePolish' , (event, word) ->
      scope.polish_text = word.word
      scope.translatePolish()

  update: (value) ->
    scope.polish_hints = []
    if(value?.length)
      scope.polishService.typing(scope.polish_text).then (results) =>
        if(results[0]? and results[0].word.toLowerCase() != scope.polish_text.toLowerCase())
          scope.polish_hints = results
        else
          scope.polish_hints = []



angular.module('app').controller 'PolishSearchController', ['$scope', 'polishService', Controller]