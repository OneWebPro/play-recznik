require
  shim: {
    "jquery-1.9.0.min.js":
      "deps": []
    "bootstrap.js":
      "deps" :["jquery-1.9.0.min.js"]
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
  "jquery-1.9.0.min",
  "bootstrap"
   "routes",
  ], (require,angular,$) ->
  require ['app-bootstrap']