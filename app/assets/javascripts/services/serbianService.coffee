class SerbianService
  urlBase = '/api/serbian'

  rootScope = undefined

  constructor: (@$http,$rootScope) ->
    rootScope = $rootScope

  typing: (word) ->
    @$http.post("#{urlBase}/find", {word: word})
    .then(
        (
          (results) ->
            results.data
        ),(
          (error) ->
            rootScope.$emit("ERROR",error.data)
        )
      )

  translate: (word) ->
    @$http.post("#{urlBase}/translate", {word: word})
    .then(
        (
          (results) ->
            results.data
        ),(
          (error) ->
            rootScope.$emit("ERROR",error.data)
        )
      )

  filter: (word, page, pageSize) ->
    @$http.post("#{urlBase}/sort/#{pageSize}/#{page}", {word: word})
    .then(
        (
          (results) ->
            results.data
        ),(
          (error) ->
            rootScope.$emit("ERROR",error.data)
        )
      )

angular.module('app').service 'serbianService', ['$http','$rootScope', SerbianService]