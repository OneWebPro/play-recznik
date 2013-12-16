class AddService
  urlBase = '/api'

  rootScope = undefined

  constructor: (@$http,$rootScope, @polishService, @serbianService) ->
    rootScope = $rootScope

  addTranslation: (polish,serbian) ->
    @$http.post("#{urlBase}/add", {
      polish : {word:polish},
      serbian:{word:serbian}
    }).then(
      (
        (results) ->
          rootScope.$emit("ADDED_TRANSLATION", results.data)
          $("#addModal").modal("hide")
      ),(
        (error) ->
          rootScope.$emit("ERROR",error.data)
          $("#addModal").modal("hide")
      )
    )


angular.module('app').service 'addService', ['$http','$rootScope' , 'polishService' , 'serbianService', AddService]