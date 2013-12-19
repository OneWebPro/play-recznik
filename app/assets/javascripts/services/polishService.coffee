class PolishService extends LanguageService

  constructor: (@$http,$rootScope) ->
    @urlBase = '/api/polish'
    @removeEvent = "REMOVED_POLISH_TRANSLATION"
    @editEvent = "EDITED_POLISH_TRANSLATION"
    super(@$http,$rootScope)

angular.module('app').service 'polishService', ['$http','$rootScope', PolishService]