(ns clj-kafka.admin
  (:require [clj-kafka.util :as util]
            [dvlopt.kafka       :as K]
            [dvlopt.kafka.admin :as admin])
  )


(defn kafka-topic [topic-name]
  (with-open [admin (admin/admin)]
    (admin/create-topics admin
                           {topic-name {::admin/number-of-partitions 2
                                        ::admin/replication-factor   1
                                        ::admin/configuration        {"cleanup.policy" "compact"}}})
    (println "Existing topics : " (keys @(admin/topics admin
                                                         {::K/internal? false}))))
)

(defn create-kafka-topic [request]
  (kafka-topic (:name (util/request-body-to-key-word request))) "Ok")

(defn list-kafka-topics []
  (with-open [admin (admin/admin)] 
    (keys @(admin/topics admin))))