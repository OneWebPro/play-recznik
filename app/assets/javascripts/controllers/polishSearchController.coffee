class PolishSearchController

  scope = undefined
  self = undefined
  searched = undefined

  constructor: ($scope, polishService, $rootScope, addService, serbianService) ->
    scope = $scope
    self = @
    scope.polish_hints = []
    scope.polish_results = []
    scope.polish_word = {}
    scope.polishService = polishService
    scope.addService = addService
    scope.serbianService = serbianService
    scope.$watch 'polish_text', debounce(@update, 500)
    scope.changePolish = (word) ->
      scope.polish_hints = []
      scope.polish_text = word
    scope.translatePolish = ->
      if(scope.polish_text?.length)
        scope.polishService.translate(scope.polish_text).then (results) =>
          scope.polish_results = results.list
          searched = results.word
      else
        scope.polish_results = []
    $rootScope.$on 'TRANSLATE_POLISH', (event, word) ->
      scope.polish_text = word.word
      scope.translatePolish()
      $('html, body').animate({scrollTop: 0}, 500)
    $rootScope.$on 'ADDED_TRANSLATION', (event, word) ->
      if(scope.polish_text?.length and scope.polish_text.toLowerCase()== word.polish.word.toLowerCase())
        if(!self.wordExists(word.serbian))
          scope.polish_results.push word.serbian
    $rootScope.$on 'EDITED_POLISH_TRANSLATION', (event, word) ->
      if(searched? and searched.id == word.id)
        scope.polish_text = word.word
        searched = word
    $rootScope.$on 'REMOVED_POLISH_TRANSLATION', (event, word) ->
      if(searched? and searched.id == word.id)
        scope.polish_text = ""
        scope.polish_hints = []
        scope.polish_results = []
    $rootScope.$on 'EDITED_SERBIAN_TRANSLATION', (event, word) ->
      result = self.findById(word.id, scope.polish_results)
      if(result?)
        result.word = word.word
    $rootScope.$on 'REMOVED_SERBIAN_TRANSLATION', (event, word) ->
      result = self.findById(word.id, scope.polish_results)
      if(result?)
        result.active = false

  wordExists: (word) ->
    for w in scope.polish_results
      if(w.id == word.id)
        return true
    false

  findById: (id, list) ->
    for w in list
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

  save: (word) ->
    scope.serbianService.edit(word)
    word.edit = false

  remove: (word) ->
    bootbox.confirm "Ta zmiana jest nieodwracalna. Kontynuować?", (result)->
      if(result)
        scope.serbianService.remove(word.id)

  addElement: ->
    if(@element?.length and searched?)
      scope.addService.addTranslation(searched.word, @element)
      @add = false
      @element = ""

angular.module('app').controller 'PolishSearchController',
  ['$scope', 'polishService', '$rootScope', 'addService', 'serbianService' , PolishSearchController]