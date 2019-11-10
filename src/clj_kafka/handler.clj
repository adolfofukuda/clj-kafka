(ns clj-kafka.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [clj-kafka.admin :as admin]
            [clj-kafka.producer :as prod]            
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (GET "/topics" [] (admin/list-kafka-topics))
  (POST "/topic" request
    (admin/create-kafka-topic request))
  (POST "/topic/:topic" request   
    (str (prod/send-n-times "1"  (:topic (:params request)) request)))
  (POST "/topic/:topic/:times" request
    (str (prod/send-n-times (:times (:params request)) (:topic (:params request)) request)))
  (route/not-found "Not Found")
  )

(def app
  (wrap-defaults app-routes (assoc-in site-defaults [:security :anti-forgery] false)))

