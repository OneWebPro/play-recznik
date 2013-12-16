class SerbianSearchController

  scope = undefined
  self = undefined

  constructor: ($scope, serbianService,$rootScope) ->
    self = @
    scope = $scope
    scope.serbian_hints = []
    scope.serbian_results = []
    scope.serbian_word = {}
    scope.serbianService = serbianService
    scope.$watch 'serbian_text', debounce(@update, 500)
    scope.changeSerbian = (word) ->
      scope.serbian_hints = []
      scope.serbian_text = word
    scope.translateSerbian = ->
      if(scope.serbian_text?.length)
        scope.serbianService.translate(scope.serbian_text).then (results) =>
          scope.serbian_results = results
      else
        scope.serbian_results = []
    $rootScope.$on 'translateSerbian' , (event, word) ->
      scope.serbian_text = word.word
      scope.translateSerbian()
    $rootScope.$on 'ADDED_TRANSLATION',(event, word) ->
      if(scope.serbian_text?.length and scope.serbian_text.toLowerCase == word.serbian.word.toLowerCase)
        if(!self.wordExists(word.polish))
          scope.serbian_results.push word.polish

  wordExists : (word) ->
    for w in scope.serbian_results
      if(w.id == word.id)
        return true
    false

  update: (value) ->
    scope.serbian_hints = []
    scope.$apply()
    if(value?.length)
      scope.serbianService.typing(scope.serbian_text).then (results) =>
        if(results.length != 0 and results[0]? and results[0].word.toLowerCase() != scope.serbian_text.toLowerCase())
          scope.serbian_hints = results
        else
          scope.serbian_hints = []



angular.module('app').controller 'SerbianSearchController', ['$scope', 'serbianService', '$rootScope', SerbianSearchController]