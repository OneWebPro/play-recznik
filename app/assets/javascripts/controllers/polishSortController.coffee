class PolishSortController

  scope = undefined
  rootScope = undefined
  self = undefined

  constructor: ($scope, polishService,$rootScope) ->
    scope = $scope
    rootScope = $rootScope
    self = @
    scope.polish_search = {
      page: 0,
      pages: 0,
      results: []
    }
    scope.polish_girdSize = 10
    scope.polishService = polishService
    scope.$watch 'polish_sort', debounce(@searchWord, 500)
    $rootScope.$on 'ADDED_TRANSLATION',(event, word) ->
      self.searchWord('')
    $rootScope.$on 'EDITED_POLISH_TRANSLATION',(event, word) ->
      find = self.findById(word.id, scope.polish_search.results)
      if(find?)
        find.word = word.word
      self.searchWord('')
    $rootScope.$on 'REMOVED_POLISH_TRANSLATION',(event, word) ->
      find = self.findById(word.id, scope.polish_search.results)
      if(find?)
        find.active = false
      self.searchWord('')

  findById: (id,list) ->
    for w in list
      if(w.id == id)
        return w
    null

  searchWord: (value) ->
    if(value != "page")
      scope.polish_search.page = 0
    scope.polishService.filter(scope.polish_sort, scope.polish_search.page, scope.polish_girdSize).then (results) =>
      scope.polish_search = results

  changeGridSize: (value) ->
    scope.polish_girdSize = value
    @searchWord('')

  nextPage: ->
    scope.polish_search.page = scope.polish_search.page + 1
    @searchWord('page')

  prevPage: ->
    scope.polish_search.page = scope.polish_search.page - 1
    @searchWord('page')

  translateWord: (word) ->
    rootScope.$emit('TRANSLATE_POLISH', word)

  getClass : (size) ->
    if(size == scope.polish_girdSize)
      "disabled"
    else
      ""

  save:(word) ->
    word.word = word.editValue
    scope.polishService.edit(word)
    word.edit = false
    word.editValue = undefined
    @searchWord('')

  remove:(id) ->
    bootbox.confirm "Ta zmiana jest nieodwracalna. Kontynuować?", (result)->
      if(result)
        scope.polishService.remove(id)
        find = self.findById(id,scope.polish_search.results)
        if(find?)
          find.active = false
        self.searchWord('')
    false

  startEdit: (element) ->
    element.editValue = element.word
    element.edit = true

  stopEdit: (element) ->
    element.editValue = undefined
    element.edit = false

angular.module('app').controller 'PolishSortController', ['$scope', 'polishService', '$rootScope', PolishSortController]