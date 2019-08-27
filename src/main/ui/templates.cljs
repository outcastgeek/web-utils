(ns ui.templates
  (:require [taoensso.timbre :as log]
            [express.web-api :refer [path-for]]))

(defn default-template-ui [data]
  (let [{:keys [content script title]} data]
    [:html {:lang "en"}
     [:header
      [:meta {:charset "utf-8"}]
      [:meta {:http-equiv "content-type" :content "text/html; charset=UTF-8"}]
      [:title (str "CLJS Express | " title)]

      [:link {:rel "dns-prefetch" :href "//cdnjs.cloudflare.com"}]
      [:link {:rel "dns-prefetch" :href "//ajax.googleapis.com"}]
      [:link {:rel "dns-prefetch" :href "//fonts.googleapis.com"}]
      [:link {:rel "dns-prefetch" :href "//code.getmdl.io"}]
      [:link {:rel "dns-prefetch" :href "//code.jquery.com"}]
      [:link {:rel "dns-prefetch" :href "//maxcdn.bootstrapcdn.com"}]

      ;;[:link {:rel "stylesheet", :href "https://unpkg.com/bulmaswatch/default/bulmaswatch.min.css"}]
      [:link {:rel "stylesheet", :href "https://unpkg.com/bulmaswatch/lux/bulmaswatch.min.css"}]

      ;;[:link {:rel "stylesheet" :href "/css/sample.css"}]
      ]
     [:body
      [:div {:class "hero is-small is-primary is-bold"}
        [:div {:class "hero-body"}
        [:div {:class "container"}
          [:h1 {:class "title"}
          "LispyPOC"]
          [:h2 {:class "subtitle"}
          "CljsExpress App"]
          ]]
        ]
      [:nav {:class "navbar is-success", :role "navigation", :aria-label "main navigation"}
        [:div {:class "navbar-brand"}
        [:a {:class "navbar-item", :href "/"}
          "CljsExpress"]
        [:button {:class "button navbar-burger"}
          [:span]
          [:span]
          [:span]
          ]]
        [:div {:class "navbar-end"}
        [:div {:class "navbar-item"}
          [:div {:class "field is-grouped"}
          [:p {:class "control"}
           [:a {:class "button is-primary", :href (path-for :react)}
            [:span {:class "icon"}
              [:i {:class "fas fa-list-alt"}]
              ]
            [:span "React"]
            ]]
          [:p {:class "control"}
            [:a {:class "button is-primary", :href (path-for :ghusers)}
            [:span {:class "icon"}
              [:i {:class "fas fa-plus-square"}]
              ]
            [:span "Github Users"]
            ]]
          ]]
        ]]

      [:div {:class "section is-medium"}
       [:div {:class "container"}
        [:div {:class "content"}
         [:div {:id "app"} "JS Loading..."]
         [:br]
         content]]]

      [:footer {:class "footer"}
       [:div {:class "container"}
        [:div {:class "content has-text-centered"}
         [:p {:dangerouslySetInnerHTML {:__html "Copyright &copy; 2019 OUCASTGEEK INC. All rights reserved."}}]
         ]]]
      [:script {:defer "defer", :src "//use.fontawesome.com/releases/v5.0.2/js/all.js"}]
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

      [:link {:rel "dns-prefetch" :href "//cdnjs.cloudflare.com"}]
      [:link {:rel "dns-prefetch" :href "//ajax.googleapis.com"}]
      [:link {:rel "dns-prefetch" :href "//fonts.googleapis.com"}]
      [:link {:rel "dns-prefetch" :href "//code.getmdl.io"}]
      [:link {:rel "dns-prefetch" :href "//code.jquery.com"}]
      [:link {:rel "dns-prefetch" :href "//maxcdn.bootstrapcdn.com"}]

      ;;[:link {:rel "stylesheet", :href "https://unpkg.com/bulmaswatch/default/bulmaswatch.min.css"}]
      [:link {:rel "stylesheet", :href "https://unpkg.com/bulmaswatch/lux/bulmaswatch.min.css"}]

      ;;[:link {:rel "stylesheet" :href "/css/sample.css"}]
      ]
     [:body
      [:div {:class "hero is-small is-primary is-bold"}
        [:div {:class "hero-body"}
        [:div {:class "container"}
          [:h1 {:class "title"}
          "LispyPOC"]
          [:h2 {:class "subtitle"}
          "CljsExpress App"]
          ]]
        ]
      [:nav {:class "navbar is-success", :role "navigation", :aria-label "main navigation"}
        [:div {:class "navbar-brand"}
        [:a {:class "navbar-item", :href "/"}
          "CljsExpress"]
        [:button {:class "button navbar-burger"}
          [:span]
          [:span]
          [:span]
          ]]
        [:div {:class "navbar-end"}
        [:div {:class "navbar-item"}
          [:div {:class "field is-grouped"}
          [:p {:class "control"}
           [:a {:class "button is-primary", :href (path-for :react)}
            [:span {:class "icon"}
              [:i {:class "fas fa-list-alt"}]
              ]
            [:span "React"]
            ]]
          [:p {:class "control"}
            [:a {:class "button is-primary", :href (path-for :ghusers)}
            [:span {:class "icon"}
              [:i {:class "fas fa-plus-square"}]
              ]
            [:span "Github Users"]
            ]]
          ]]
        ]]

      [:div {:class "section is-medium"}
       [:div {:class "container"}
        [:div {:class "content"}
         [:div {:id "app"} "JS Loading..."]
         [:br]
         content]]]



      [:footer {:class "footer"}
       [:div {:class "container"}
        [:div {:class "content has-text-centered"}
         [:p {:dangerouslySetInnerHTML {:__html "Copyright &copy; 2019 OUCASTGEEK INC. All rights reserved."}}]
         ]]]
      [:script {:defer "defer", :src "//use.fontawesome.com/releases/v5.0.2/js/all.js"}]
      [:script {:type "text/javascript" :src script}]
      ]]
    ))

;;;;;;;;;;;;;;;;;;;;;; Widgets

(defn raw-str-widget-ui [data]
  (log/debug "Raw Stringing...")
  (let [{:keys [text]} data]
    ;;[:div text]
    [:div {:class "columns is-mobile is-centered"}
      [:div {:class "column is-half"}
        [:div {:class "box"}
          [:article {:class "media"}
            [:div {:class "media-left"}
              [:figure {:class "image is-64x64"}
               [:img {:src "//via.placeholder.com/128/4BBF73/000000?text=CljExpress"
                      :alt "CljExpress"}]
              ]]
            [:div {:class "media-content"}
              [:div {:class "content"}
              [:p
               [:strong "Welcome to CljExpress!"]
               [:br]
               [:small "This Application is a Clojurescript POC!"]
               [:br]
               [:strong text]
                ]]
              [:nav {:class "level is-mobile"}
              [:div {:class "level-left"}
                [:a {:class "level-item"
                    :aria-label "list something"
                    :href "#someList"}
                [:span {:class "icon is-small"}
                  [:i {:class "fas fa-list-alt", :aria-hidden "true"}]
                  ]]
                [:a {:class "level-item"
                    :aria-label "create something"
                    :href "#createSomething"}
                [:span {:class "icon is-small"}
                  [:i {:class "fas fa-plus-square", :aria-hidden "true"}]
                  ]]
                ]]
            ]]
        ]]]
    ))

(defn un-bouton-ui [data]
  (log/debug "Buttoning ...")
  (let [{:keys [text]} data]
    [:div
     [:hr]
     [:input {
              :type "submit"
              :class "btn btn-default"
              :value text}]
     ]))


(defn hello-ui [data]
  (log/debug "Rendering ...")
  (let [{:keys [upper-bound]} data]
    [:div "Hello world!"
     [:ul (for [n (range 1 upper-bound)]
            [:li {:key n} n])]
     [un-bouton-ui {:text "React!!"}]]))

