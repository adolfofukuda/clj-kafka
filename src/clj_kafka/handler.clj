(ns clj-kafka.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-kafka.admin :as admin]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/topics" [] (admin/list-kafka-topics))
  (POST "/topic" request
    (admin/create-kafka-topic request))
  (route/not-found "Not Found")
  )

(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))

