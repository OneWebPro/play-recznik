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
    scope.$watch 'polish_sort', debounce(@search, 500)
    $rootScope.$on 'ADDED_TRANSLATION',(event, word) ->
      @search('')
    $rootScope.$on 'EDITED_POLISH_TRANSLATION',(event, word,editWord) ->
      find = self.findById(word.id, scope.polish_search)
      if(find?)
        find.word = word.word
    $rootScope.$on 'REMOVED_POLISH_TRANSLATION',(event, word,removeId) ->
      find = self.findById(word.id, scope.polish_search)
      if(find?)
        find.active = false

  findById: (id,list) ->
    for w in list
      if(w.id == id)
        return w
    null

  search: (value) ->
    if(value != "page")
      scope.polish_search.page = 0
    scope.polishService.filter(scope.polish_sort, scope.polish_search.page, scope.polish_girdSize).then (results) =>
      scope.polish_search = results

  changeGridSize: (value) ->
    scope.polish_girdSize = value
    @search('')

  nextPage: ->
    scope.polish_search.page = scope.polish_search.page + 1
    @search('page')

  prevPage: ->
    scope.polish_search.page = scope.polish_search.page - 1
    @search('page')

  translateWord: (word) ->
    rootScope.$emit('TRANSLATE_POLISH', word)

  getClass : (size) ->
    if(size == scope.polish_girdSize)
      "disabled"
    else
      ""

  save:(word) ->
    scope.polishService.edit(word)

  remove:(id) ->
    scope.polishService.remove(id)


angular.module('app').controller 'PolishSortController', ['$scope', 'polishService', '$rootScope', PolishSortController]