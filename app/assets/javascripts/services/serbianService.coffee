class Service
  urlBase = '/api/serbian'

  constructor: (@$http) ->

  typing: (word) ->
    @$http.get("#{urlBase}/find/#{word}")
    .then (results) ->
        results.data

  translate: (word) ->
    @$http.get("#{urlBase}/translate/#{word}")
    .then (results) ->
        results.data

angular.module('app').service 'serbianService', ['$http', Service]