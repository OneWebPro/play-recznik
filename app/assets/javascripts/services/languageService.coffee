class window.LanguageService

  rootScope = undefined

  config = {headers:{
    'Content-Type': 'application/json; charset=utf-8',
  }};

  self = undefined

  constructor: (@$http, $rootScope) ->
    self = @
    rootScope = $rootScope

  typing: (word) ->
    @$http.post("#{@urlBase}/find", {word : word})
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
    @$http.post("#{@urlBase}/translate", {word : word})
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
    @$http.post("#{@urlBase}/sort/#{pageSize}/#{page}", {word: word})
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
    @$http.post("#{@urlBase}/save", {id:word.id,word:word.word}).then(
      (
        (results) ->
          rootScope.$emit(self.editEvent, results.data)
          results.data
      ),(
        (error) ->
          rootScope.$emit("ERROR",error.data)
      )
    )

  remove: (id) ->
    @$http.post("#{@urlBase}/remove/#{id}",config).then(
      (
        (results) ->
          rootScope.$emit(self.removeEvent, results.data)
          results.data
      ), (
        (error) ->
          rootScope.$emit("ERROR", error.data)
      )
    )
