class SerbianSortController

  scope = undefined
  rootScope = undefined
  self = undefined

  constructor: ($scope, serbianService,$rootScope) ->
    scope = $scope
    rootScope = $rootScope
    self = @
    scope.serbian_search = {
      page: 0,
      pages: 0,
      results: []
    }
    scope.serbian_girdSize = 10
    scope.serbianService = serbianService
    scope.$watch 'serbian_sort', debounce(@search, 500)
    $rootScope.$on 'ADDED_TRANSLATION',(event, word) ->
      @search('')
    $rootScope.$on 'EDITED_SERBIAN_TRANSLATION',(event, word) ->
      find = self.findById(word.id,scope.serbian_search)
      if(find?)
        find.word = word.word
    $rootScope.$on 'REMOVED_SERBIAN_TRANSLATION',(event, word) ->
      find = self.findById(word.id,scope.serbian_search)
      if(find?)
        find.active = false

  findById: (id,list) ->
    for w in list
      if(w.id == id)
        return w
    null

  search: (value) ->
    if(value != "page")
      scope.serbian_search.page = 0
    scope.serbianService.filter(scope.serbian_sort, scope.serbian_search.page, scope.serbian_girdSize).then (results) =>
      scope.serbian_search = results

  changeGridSize: (value) ->
    scope.serbian_girdSize = value
    @search('')

  nextPage: ->
    scope.serbian_search.page = scope.serbian_search.page + 1
    @search('page')

  prevPage: ->
    scope.serbian_search.page = scope.serbian_search.page - 1
    @search('page')

  translateWord: (word) ->
    rootScope.$emit('TRANSLATE_SERBIAN', word)

  getClass : (size) ->
    if(size == scope.serbian_girdSize)
      "disabled"
    else
      ""

  save:(word) ->
    scope.serbianService.edit(word)
    word.edit = false
    @search('')

  remove:(word) ->
   bootbox.confirm "Ta zmiana jest nieodwracalna. Kontynuować?", (result)->
     if(result)
       scope.serbianService.remove(word.id)
       @search('')

angular.module('app').controller 'SerbianSortController', ['$scope', 'serbianService', '$rootScope', SerbianSortController]