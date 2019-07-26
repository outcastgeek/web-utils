(ns ui.templates
  (:require [cljs.core.match :refer-macros [match]]
            [reagent.core :as r]
            [reagent.dom.server :as rs]))

(defn default-template-ui [data]
  (let [{:keys [content script title]} data]
    [:html {:lang "en"}
     [:header
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "content-type" :content "text/html; charset=UTF-8"}]
      [:title (str "CLJS Express | " title)]
      [:link {:rel "stylesheet"
              :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"}]
      [:link {:rel "stylesheet"
              :href "/css/sample.css"}]
      ]
     [:body
      [:div {:class "container"}
       [:div {:class "jumbotron"}
        [:h1 "CLJS Express"]]
       [:div {:class "row"}
        [:div {:id "app"
               :class "col-lg-12"} content]]]
      [:script {:type "text/javascript" :src "https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"}]
      [:script {:type "text/javascript" :src "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"}]
      [:script {:type "text/javascript" :src script}]
      ]]
    ))

(defn raw-template-ui [data]
  (let [{:keys [content script title]} data]
    [:html {:lang "en"}
     [:header
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "content-type" :content "text/html; charset=UTF-8"}]
      [:title (str "RAW CLJS Express | " title)]
      [:link {:rel "stylesheet"
              :href "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"}]
      [:link {:rel "stylesheet"
              :href "/css/sample.css"}]
      ]
     [:body
      [:div {:class "container"}
       [:div {:class "jumbotron"}
        [:h1 "RAW CLJS Express"]]
       [:div {:class "row"}
        [:div {:id "app"
               :class "col-lg-12"} content]]]
      [:script {:type "text/javascript" :src "https://ajax.googleapis.com/ajax/libs/jquery/2.1.4/jquery.min.js"}]
      [:script {:type "text/javascript" :src "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"}]
      [:script {:type "text/javascript" :src script}]
      ]]
    ))

;;;;;;;;;;;;;;; Rendering

(defn- render-to-str [widget]
  (rs/render-to-string widget))

(defn render
  [resp]
  (match resp
         [:default data]
         (str
          "<!DOCTYPE html>"
          (render-to-str [default-template-ui data])
          )
         [:raw data]
         (str
          "<!DOCTYPE html>"
          (render-to-str [raw-template-ui data])
          )
         [_ data] data))

;;;;;;;;;;;;;;;;;;;;;; Widgets

(defn raw-str-widget-ui [data]
  (println "Raw Stringing...")
  (let [{:keys [text]} data]
    [:div text]
    ))

(defn un-bouton-ui [data]
  (println "Buttoning ...")
  (let [{:keys [text]} data]
    [:div
     [:hr]
     [:input {
              :type "submit"
              :class "btn btn-default"
              :value text}]
     ]))


(defn hello-ui [data]
  (println "Rendering ...")
  (let [{:keys [upper-bound]} data]
    [:div "Hello world!"
     [:ul (for [n (range 1 upper-bound)]
            [:li {:key n} n])]
     [un-bouton-ui {:text "React!!"}]]))

