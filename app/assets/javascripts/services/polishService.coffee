class Service
  urlBase = '/api/polish'

  constructor: (@$http) ->

  typing: (word) ->
    @$http.post("#{urlBase}/find", {word : word})
    .then (results) ->
        results.data

  translate: (word) ->
    @$http.post("#{urlBase}/translate", {word : word})
    .then (results) ->
        results.data

  filter: (word, page, pageSize) ->
    @$http.post("#{urlBase}/sort/#{pageSize}/#{page}", {word: word})
    .then (results) ->
        results.data

angular.module('app').service 'polishService', ['$http', Service]