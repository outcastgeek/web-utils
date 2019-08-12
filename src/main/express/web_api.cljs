(ns express.web-api
  (:require [cljs.core.match :refer-macros [match]]
            [reagent.dom.server :as rs]
            [express.sugar :as ex]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Rendering
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn- render-to-str [widget]
  (rs/render-to-string widget))

(defn- clj->json [data]
  (.. js/JSON stringify (clj->js data)))

(defn- render
  ([body] (pr-str body))
  ([format body]
   (condp = format
     :html (str
            "<!DOCTYPE html>"
            (render-to-str body))
     :json (clj->json body)
     (pr-str body)
     )))

(defn- build-response
  ([body]
   (identity
    {:status 200
     :headers {}
     :body (apply render body)}))
  ([body options]
   (let [{:keys [headers status] :or {headers {}, status 200}} options]
     {:status status
      :headers headers
      :body (apply render body)})))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; API
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn respond
  [res & data]
  (println "Responding with data: " data)
  (let [{status :status headers :headers body :body} (apply build-response [data])]
    (-> res
        (ex/status status)
        (ex/headers (clj->js headers))
        (ex/send body))))

