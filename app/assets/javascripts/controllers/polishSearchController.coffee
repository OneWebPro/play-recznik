class PolishSearchController

  scope = undefined
  self = undefined
  searched = undefined

  constructor: ($scope, polishService,$rootScope,addService) ->
    scope = $scope
    self = @
    scope.polish_hints = []
    scope.polish_results = []
    scope.polish_word = {}
    scope.polishService = polishService
    scope.addService = addService
    scope.$watch 'polish_text', debounce(@update, 500)
    scope.changePolish = (word) ->
      scope.polish_hints = []
      scope.polish_text = word
    scope.translatePolish = ->
      if(scope.polish_text?.length)
        scope.polishService.translate(scope.polish_text).then (results) =>
          scope.polish_results = results
          searched = scope.polish_text
      else
        scope.polish_results = []
    $rootScope.$on 'TRANSLATE_POLISH' , (event, word) ->
      scope.polish_text = word.word
      scope.translatePolish()
      $('html, body').animate({scrollTop: 0}, 500)
    $rootScope.$on 'ADDED_TRANSLATION',(event, word) ->
      if(scope.polish_text?.length and scope.polish_text.toLowerCase == word.polish.word.toLowerCase)
        if(!self.wordExists(word.serbian))
          scope.polish_results.push word.serbian

  wordExists : (word) ->
    for w in scope.polish_results
      if(w.id == word.id)
        return true
    false

  findById: (id) ->
    for w in scope.polish_results
      if(w.id == id)
        return w
    null

  update: (value) ->
    scope.polish_hints = []
    scope.$apply()
    if(value?.length)
      scope.polishService.typing(scope.polish_text).then (results) =>
        if(results.length != 0 and results[0]? and results[0].word.toLowerCase() != scope.polish_text.toLowerCase())
          scope.polish_hints = results
        else
          scope.polish_hints = []

  save:(word) ->
    false

  remove:(id) ->
    false

  addElement: ->
    if(@element?.length and searched?.length)
      scope.addService.addTranslation(searched,@element)
      @add = false
      @element = ""

angular.module('app').controller 'PolishSearchController', ['$scope', 'polishService', '$rootScope','addService', PolishSearchController]