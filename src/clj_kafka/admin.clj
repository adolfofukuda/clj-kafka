(ns clj-kafka.admin
  (:require [clojure.data.json :as json]
            [kinsky.admin :as admin])
  )

(def c (admin/client {"bootstrap.servers" "localhost:9092"}))

(defn convert-json-to-key-word [data]
  (json/read-str (slurp (:body data)) :key-fn keyword))

(defn kafka-topic [topic-name]
  (admin/create-topic c topic-name {:partitions 2 :replication-factor 1 :config {:cleanup.policy "compact"}})
)

(defn create-kafka-topic [request]
  (kafka-topic (:name (convert-json-to-key-word request))) "Ok")

(defn list-kafka-topics []
  (admin/list-topics c false))