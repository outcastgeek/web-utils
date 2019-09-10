(ns core
  (:require-macros [utils.macros :as m])
  (:require [cljs.nodejs :as nodejs]
            [taoensso.timbre :as log]
            [express.sugar :as ex]
            [express.web-api :as web]
            [endpoints :as ep]
            [routing :refer [routing-data]]
            ["morgan" :as logger]
            ["serve-static" :as serve-static]
            ["xhr2" :as xhr2]
            ["body-parser" :as body-parser]
            ["cookie-parser" :as cookie-parser]
            ["csurf" :as csurf]
            ["helmet" :as helmet]))

(nodejs/enable-util-print!)

(set! js/XMLHttpRequest xhr2)

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
  (let [staticFolder (if-let [STATIC (m/env-var "STATIC")] STATIC "static")
        portNumber (if-let [PORT (m/env-var "PORT")] PORT 8080)]
    (log/debug "Static Folder: " staticFolder)
    (log/debug "Port Number: " portNumber)
    (-> (ex/app)
        (ex/with-middleware (serve-static staticFolder (clj->js {:index false})))
        (ex/with-middleware (helmet))
        (ex/with-middleware (logger "combined")) ; Logger
        (ex/with-middleware (body-parser/json)) ;; support json encoded bodies
        (ex/with-middleware (body-parser/urlencoded (clj->js {:extended true}))) ;; support encoded bodies
        (ex/with-middleware (cookie-parser))
        (ex/with-middleware (csurf (clj->js {:cookie true})))
        (ex/with-middleware "/" routes)
        (ex/listen portNumber))
    ))

(set! *main-cli-fn* main)

