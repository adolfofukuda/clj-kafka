(ns clj-kafka.util
  (:require [clojure.data.json :as json]))

(defn request-body-to-key-word [data]
  (json/read-str (slurp (:body data)) :key-fn keyword))

(defn parse-int [s]
  (Integer. (re-find  #"\d+" s)))