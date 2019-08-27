(ns core
  (:require [cljs.nodejs :as nodejs]
            [taoensso.timbre :as log]
            ["morgan" :as logger]
            [util.os :as os]
            [express.sugar :as ex]
            [express.web-api :as web]
            [endpoints :as ep]
            [routing :refer [routing-data]]))

(nodejs/enable-util-print!)

(set! js/XMLHttpRequest (nodejs/require "xhr2"))

(defmulti handle (fn [req-data] (:endpoint req-data)))

(defmethod handle :home [req-data]
  (ep/say-hello! (:req req-data)))

(defmethod handle :react [req-data]
  (ep/render-widget (:req req-data)))

(defmethod handle :weather [req-data]
  (ep/check-weather (:req req-data)))

(defmethod handle :ghusers [req-data]
  (ep/check-github-users (:req req-data)))

(defmethod handle :start [req-data]
  (ep/app-start (:req req-data)))

(defmethod handle :health [req-data]
  (ep/check-health (:req req-data)))

(defmethod handle :stop [req-data]
  (ep/app-stop (:req req-data)))

(defmethod handle :foo [req-data]
  (ep/say-hello! (:req req-data)))

(defmethod handle :default [_]
  (web/send "Not Found"))

(def routes
  (web/routes
   routing-data
   handle))

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
