require
  baseUrl: "javascripts"
  shim:
    "angular/angular.min.js":
      deps: []
    "angular/angular-animate.min":
      "deps": ["angular/angular.min"]
    "angular/angular-mocks":
      "deps": ["angular/angular.min"]
    "angular/angular-route.min":
      "deps": ["angular/angular.min"]
    "app":
      "deps": ["angular/angular-animate.min", "angular/angular-mocks", "angular/angular-route.min"]
      require["bootstrap"]
