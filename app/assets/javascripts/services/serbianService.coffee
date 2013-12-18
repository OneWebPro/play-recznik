class SerbianService extends LanguageService

  constructor: (@$http,$rootScope) ->
    @urlBase = '/api/serbian'
    @removeEvent = "REMOVED_SERBIAN_TRANSLATION";
    @editEvent = "EDITED_SERBIAN_TRANSLATION"
    super(@$http,$rootScope)

angular.module('app').service 'serbianService', ['$http','$rootScope', SerbianService]