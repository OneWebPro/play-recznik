class Route
  constructor: ($routeProvider,$locationProvider) ->
    $routeProvider
    .when "/main",
        templateUrl: 'view/body'
    .otherwise
        redirectTo: '/main'

angular.module('app').config ['$routeProvider','$locationProvider', Route]