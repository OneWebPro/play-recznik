require
  shim: {
    "jquery-1.9.0.min":
      "deps": []
    "bootstrap":
      "deps" :["jquery-1.9.0.min"]
    "bootbox.min" :
      "deps": ["jquery-1.9.0.min", "bootstrap"]
    "growl":
      "deps": ["jquery-1.9.0.min", "bootstrap"]
    "jqueryEvents":
      "deps" :["jquery-1.9.0.min"]
    "angular/angular":
      "deps": []
    "angular/angular-animate":
      "deps": ["angular/angular"]
    "angular/angular-route":
      "deps": ["angular/angular"]
    "app":
      "deps": ["angular/angular","angular/angular-animate", "angular/angular-route"]
    "routes":
      "deps": ["app"]
    ### --- Serbian --- ###
    "services/serbianService":
      "deps": ["app"]
    "controllers/serbianSearchController":
      "deps" : ["app"]
    "controllers/serbianSortController":
      "deps" : ["app"]
    ### --- Polish --- ###
    "services/polishService":
      "deps": ["app"]
    "controllers/polishSearchController":
      "deps" : ["app"]
    "controllers/polishSortController":
      "deps" : ["app"]
    ### --- Add --- ###
    "services/addService":
      "deps": ["app"]
    "controllers/addController":
      "deps": ["app"]
    ### --- Others --- ###
    "controllers/siteController":
      "deps": ["app"]
  }
  ["require"
   "jquery-1.9.0.min"
   "bootstrap"
   "bootbox.min"
   "growl"
   "jqueryEvents"
   "controllers/siteController"
   "controllers/serbianSearchController"
   "controllers/serbianSortController"
   "services/serbianService"
   "controllers/polishSearchController"
   "controllers/polishSortController"
   "services/polishService"
   "controllers/addController"
   "services/addService"
   "routes"
  ], (require) ->
  require ['angular/angular','app-bootstrap']