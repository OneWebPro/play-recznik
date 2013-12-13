class Route
  constructor: ($routeProvider) ->
    $routeProvider
    .otherwise
        redirectTo: '/main'

angular.module('app').config ['$routeProvider', Route]