class AddController

  scope = undefined
  rootScope = undefined

  constructor: ($scope, service, $rootScope) ->
    scope = $scope
    rootScope = $rootScope
    scope.service = service
    scope.polish_hintsAdd = []
    scope.serbian_hintsAdd = []
    scope.$watch 'polish_add', debounce(@updatePolish, 500)
    scope.$watch 'serbian_add', debounce(@updateSerbian, 500)


  updatePolish: (value) ->
    scope.polish_hintsAdd = []
    scope.$apply()
    if(value?.length)
      scope.service.polishService.typing(scope.polish_add).then (results) =>
        if(results.length != 0 and results[0]? and results[0].word.toLowerCase() != scope.polish_add.toLowerCase())
          scope.polish_hintsAdd = results
        else
          scope.polish_hintsAdd = []

  updateSerbian: (value) ->
    scope.serbian_hintsAdd = []
    scope.$apply()
    if(value?.length)
      scope.service.serbianService.typing(scope.serbian_add).then (results) =>
        if(results.length != 0 and results[0]? and results[0].word.toLowerCase() != scope.serbian_add.toLowerCase())
          scope.serbian_hintsAdd = results
        else
          scope.serbian_hintsAdd = []

  changePolish: (word) ->
    scope.polish_add = word

  changeSerbian: (word) ->
    scope.serbian_add = word

  save: ->
    if(scope.polish_add?.length and scope.serbian_add?.length)
      scope.service.addTranslation(scope.polish_add, scope.serbian_add)
      scope.polish_add = ""
      scope.serbian_add = ""


angular.module('app').controller 'AddController', ['$scope', 'addService', '$rootScope', AddController]