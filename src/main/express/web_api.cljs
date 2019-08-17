(ns express.web-api
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [cljs.core.async :as async :refer [<!]]
            [cljs.core.match :refer-macros [match]]
            [clojure.pprint :refer [pprint]]
            [reagent.dom.server :as rs]
            [express.sugar :as ex]
            [bidi.bidi :as bidi]
            [lambdaisland.uri :as uri]))

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


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; ROUTING
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

; (defn channel?
;   [x] ;; Clojure
;   (satisfies? clojure.core.async.impl.protocols/Channel x))

(defn- channel?
  [x] ;; Clojurescript
  (satisfies? cljs.core.async.impl.protocols/Channel x))

(defn- respond
    [res data]
    (let [{status :status headers :headers body :body} data]
        (println "Response Data: " (-> data pprint with-out-str))
        (-> res
            (ex/status status)
            (ex/set-headers (clj->js headers))
            (ex/send body))))

(defn match-route [rts path method]
  (println "Matching: " path " and " method)
  (let [match-data (or
                    (bidi/match-route*
                     rts
                     path
                     {:request-method method})
                    {:route-params {}
                     :query-params {}
                     :request-method method
                     :handler (fn [req]
                                (send "Not Found"))
                     })]
    (println "Matched Route Data: " (-> match-data pprint with-out-str))
    match-data))

(defn- route-dispatcher [rts req res]
  (let [headers (ex/get-headers req)
        path (ex/path req)
        full-url (ex/full-url req)
        method (ex/method req)
        match-data (match-route rts path method)
        {route-params :route-params
         query-params :query-params
         request-method :request-method
         endpoint-fn :handler} match-data
        data (endpoint-fn
              {:headers headers
               :route-params route-params
               :query-params query-params
               :path path
               :full-url full-url
               :request-method request-method
               :raw-req req})]
    (if (channel? data)
        (go
            (let [response-data (<! data)]
                (println "Response Data: " (-> response-data pprint with-out-str))
                (respond res response-data)))
        (do
            (println "Response Data: " (-> data pprint with-out-str))
            (respond res data))
        )))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; API
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn routes[rts]
  (ex/routes [:all "*" (fn [req res]
                         ;;(println "Route Definitions: " (-> rts pprint with-out-str))
                         (route-dispatcher rts req res))]))

(def path-for bidi/path-for)

(defn send
  [& data]
  (apply build-response [data]))

