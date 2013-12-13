require
  shim: {
    "angular/angular.min":
      "deps": []
    "angular/angular-animate.min":
      "deps": ["angular/angular.min"]
    "angular/angular-mocks":
      "deps": ["angular/angular.min"]
    "angular/angular-route.min":
      "deps": ["angular/angular.min"]
    "app":
      "deps": ["angular/angular-animate.min", "angular/angular-mocks", "angular/angular-route.min"]
    "routes":
      "deps": ["app"]
    ###"backend/gitHubBackend": {
      "deps": ["app"]
    },###
  }
  ["require",
   "angular/angular.min"
   "routes",
  ], (require) ->
  require ['bootstrap']