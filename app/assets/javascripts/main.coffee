require
  shim: {
    "jquery-1.9.0.min":
      "deps": []
    "bootstrap.js":
      "deps" :["jquery-1.9.0.min"]
    "jqueryEvents":
      "deps" :["jquery-1.9.0.min"]
    "angular/angular.min":
      "deps": []
    "angular/angular-animate.min":
      "deps": ["angular/angular.min"]
    "angular/angular-route.min":
      "deps": ["angular/angular.min"]
    "app":
      "deps": ["angular/angular.min","angular/angular-animate.min", "angular/angular-route.min"]
    "routes":
      "deps": ["app"]
    "services/serbianService":
      "deps": ["app"]
    "controllers/serbianSearchController":
      "deps" : ["app"]
    "controllers/serbianSortController":
      "deps" : ["app"]
    "services/polishService":
      "deps": ["app"]
    "controllers/polishSearchController":
      "deps" : ["app"]
  }
  ["require"
   "angular/angular.min"
   "jquery-1.9.0.min"
   "bootstrap"
   "jqueryEvents"
   "controllers/serbianSearchController"
   "controllers/serbianSortController"
   "services/serbianService"
   "controllers/polishSearchController"
   "services/polishService"
   "routes"
  ], (require,angular) ->
  require ['app-bootstrap']