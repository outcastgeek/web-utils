(ns core
  (:require [cljs.nodejs :as nodejs]
            ["morgan" :as logger]
            [util.os :as os]
            [express.sugar :as ex]
            [endpoints :as ep]))

(nodejs/enable-util-print!)

(set! js/XMLHttpRequest (nodejs/require "xhr2"))

(def routes
  (ex/routes
   [:get "/" ep/say-hello!]
   [:get "/react" ep/render-widget]
   [:get "/weather/:city" ep/check-weather]
   [:get "/github-users" ep/check-github-users]
   [:get "/_ah/start" ep/app-start]
   [:get "/_ah/health" ep/check-health]
   [:get "/_ah/stop" ep/app-stop]
   [:get "/_ah/stop" ep/app-stop]
   [:all "/foo" ep/say-hello!]))

(defn main []
  (let [staticFolder (if-let [STATIC (os/env "STATIC")] STATIC "static")
        portNumber (if-let [PORT (os/env "PORT")] PORT 8080)]
    (println "Static Folder: " staticFolder)
    (println "Port Number: " portNumber)
    (-> (ex/app)
        (ex/static staticFolder)
        ;;(ex/static (if-let [STATIC (os/env "STATIC")] STATIC "static") "/public")
        (ex/with-middleware (logger "combined")) ; Logger
        (ex/with-middleware "/" routes)
        (ex/listen portNumber))
    ))

(set! *main-cli-fn* main)
