class SerbianService extends LanguageService

  rootScope = undefined

  constructor: (@$http,$rootScope) ->
    rootScope = $rootScope
    @urlBase = '/api/serbian'
    @removeEvent = "REMOVED_SERBIAN_TRANSLATION"
    @editEvent = "EDITED_SERBIAN_TRANSLATION"
    super(@$http,$rootScope)


angular.module('app').service 'serbianService', ['$http','$rootScope', SerbianService]