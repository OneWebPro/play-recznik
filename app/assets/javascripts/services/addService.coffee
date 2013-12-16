class AddService
  urlBase = '/api'

  constructor: (@$http, @polishService, @serbianService) ->

  addTranslation: (polish,serbian) ->
    @$http.post("#{urlBase}/add", {
      polish : {word:polish},
      serbian:{word:serbian}
    }).then (results) ->
        results.data


angular.module('app').service 'addService', ['$http' , 'polishService' , 'serbianService', AddService]