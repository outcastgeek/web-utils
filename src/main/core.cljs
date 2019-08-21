(ns core
  (:require [cljs.nodejs :as nodejs]
            [taoensso.timbre :as log]
            ["morgan" :as logger]
            [util.os :as os]
            [express.sugar :as ex]
            [express.web-api :as web]
            [endpoints :as ep]))

(nodejs/enable-util-print!)

(set! js/XMLHttpRequest (nodejs/require "xhr2"))

(def routes
  (web/routes
   ["/"
    {"" {:get
         {"" ep/say-hello!}}
     "react" {:get
              {"" ep/render-widget}}
     ["weather/" :city] {:get
                         {"" ep/check-weather}}
     "github-users" {:get
                     {"" ep/check-github-users}}
     "_ah/start" {:get
                  {"" ep/app-start}}
     "_ah/health" {:get
                   {"" ep/check-health}}
     "_ah/stop" {:get
                 {"" ep/app-stop}}
     "foo" ep/say-hello!}
    ]))

(defn main []
  (let [staticFolder (if-let [STATIC (os/env "STATIC")] STATIC "static")
        portNumber (if-let [PORT (os/env "PORT")] PORT 8080)]
    (log/debug "Static Folder: " staticFolder)
    (log/debug "Port Number: " portNumber)
    (-> (ex/app)
        (ex/static staticFolder)
        ;;(ex/static (if-let [STATIC (os/env "STATIC")] STATIC "static") "/public")
        (ex/with-middleware (logger "combined")) ; Logger
        (ex/with-middleware "/" routes)
        (ex/listen portNumber))
    ))

(set! *main-cli-fn* main)
