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

  edit: (word) ->
    @$http.post("#{urlBase}/save", {id: word.id, word: word.word}).then(
      (
        (results) ->
          rootScope.$emit("EDITED_SERBIAN_TRANSLATION", results.data,word)
      ), (
        (error) ->
          rootScope.$emit("ERROR", error.data)
      )
    )

  remove: (id) ->
    @$http.get("#{urlBase}/remove/#{id}").then(
      (
        (results) ->
          rootScope.$emit("REMOVED_SERBIAN_TRANSLATION", results.data,id)
      ),(
        (error) ->
          rootScope.$emit("ERROR",error.data)
      )
    )

angular.module('app').service 'serbianService', ['$http','$rootScope', SerbianService]