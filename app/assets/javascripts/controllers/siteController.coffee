class SiteController

  constructor: ($scope, $rootScope) ->
    $rootScope.$on "ERROR", @error
    $scope.$on "ERROR", @error

  error: (event, error) ->
    if(error?.length)
      $.bootstrapGrowl error,
        ele: 'body'
        type: 'danger'
        offset:
          from: 'bottom', amount: 20
          align: 'right'
          width: 250,
          delay: 4000
          allow_dismiss: true
          stackup_spacing: 10

angular.module('app').controller 'SiteController', ['$scope','$rootScope', SiteController]