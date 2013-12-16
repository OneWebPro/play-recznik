class AddService

  constructor: (@$http, @polishService, @serbianService) ->

  typingPolish = (word) ->
    @polishService.typing(word)

  typingSerbian = (word) ->
    @serbianService.typing(word)

#  addTranslation = (polish,serbian) ->


angular.module('app').service 'addService', ['$http' , 'polishService' , 'serbianService', AddService]