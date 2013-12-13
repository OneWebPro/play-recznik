require
  shim: {
    "angular/angular.min":
      "deps": []
    "angular/angular-animate.min":
      "deps": ["angular/angular.min"]
    "angular/angular-route.min":
      "deps": ["angular/angular.min"]
    "app":
      "deps": ["angular/angular-animate.min", "angular/angular-route.min"]
    "routes":
      "deps": ["app"]
    ###"backend/gitHubBackend": {
      "deps": ["app"]
    },###
  }
  ["require",
   "angular/angular.min",
   "routes",
  ], (require,angular) ->
  require ['bootstrap']