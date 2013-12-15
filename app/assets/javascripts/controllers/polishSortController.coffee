class PolishSortController

  scope = undefined

  constructor: ($scope, polishService) ->
    scope = $scope
    scope.polish_search = {
      page: 0,
      pages: 0,
      results: []
    }
    scope.polish_girdSize = 10
    scope.polishService = polishService
    scope.$watch 'polish_sort', debounce(@search, 500)

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
    scope.$emit('translatePolish', word)

  getClass : (size) ->
    if(size == scope.polish_girdSize)
      "disabled"
    else
      ""


angular.module('app').controller 'PolishSortController', ['$scope', 'polishService', PolishSortController]