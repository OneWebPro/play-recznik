class Service
  urlBase = '/api/polish'

  constructor: (@$http) ->

  typing: (word) ->
    @$http.get("#{urlBase}/find/#{word}")
    .then (results) ->
        results.data

angular.module('app').service 'polishService', ['$http', Service]