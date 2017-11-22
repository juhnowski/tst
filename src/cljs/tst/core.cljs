(ns tst.core
    (:require [reagent.core :as reagent :refer [atom]]
              [secretary.core :as secretary :include-macros true]
              [accountant.core :as accountant]))

;; -------------------------
;; Views
(defn lister [items]
  [:ul
   (for [item items]
     ^{:key item} [:li "Item " item])]
)

(defn lister-user []
  [:div
   "Here is a list:"
   [lister (range 3)]]
)

(defn hello-component [name]
  [:p "Hello, " name "!"])

(defn home-page []
  [:div [:h2 "Welcome to tst"]
   [:div [:a {:href "/about"} "go to about page"]
   [hello-component "Ilya"]
   [lister-user]]

   [:div [:a {:href "/click"} "go to click-counter page"]
]])

(def click-count (reagent/atom 0))

(defn counting-component []
  [:div
   "The atom " [:code "click-count"] " has value: "
   @click-count ". "
   [:input {:type "button" :value "Click me!"
            :on-click #(swap! click-count inc)}]])

(defn click-page []
  [:div [:h2 "Click-counter test"]
    [counting-component]
  ]
)

(defn simple-component []
    [:div
     [:p "I am a component!"]
     [:p.someclass
      "I have " [:strong "bold"]
     [:span {:style {:color "red"}} " and red "] "text."]]
)


(defn about-page []
  [:div [:h2 "About tst"]
   [:div [:a {:href "/"} "go to the home page"]
   [simple-component]]])



;; -------------------------
;; Routes

(def page (atom #'home-page))

(defn current-page []
  [:div [@page]])

(secretary/defroute "/" []
  (reset! page #'home-page))

(secretary/defroute "/about" []
  (reset! page #'about-page))

  (secretary/defroute "/click" []
    (reset! page #'click-page))
;; -------------------------
;; Initialize app

(defn mount-root []
  (reagent/render [current-page] (.getElementById js/document "app")))

(defn init! []
  (accountant/configure-navigation!
    {:nav-handler
     (fn [path]
       (secretary/dispatch! path))
     :path-exists?
     (fn [path]
       (secretary/locate-route path))})
  (accountant/dispatch-current!)
  (mount-root))
