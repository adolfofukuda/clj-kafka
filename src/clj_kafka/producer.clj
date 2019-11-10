(ns clj-kafka.producer
  (:require [dvlopt.kafka       :as K]
            [dvlopt.kafka.out   :as K.out]
            [clj-kafka.util :as util]))

(defn- async-send [topic key value]
    (with-open [producer (K.out/producer {::K/nodes             [["localhost" 9092]]
                                          ::K/serializer.key     :string
                                          ::K/serializer.value   :string
                                          ::K.out/configuration {"client.id" "my-producer"}})]
      (K.out/send producer
                  {::K/topic topic
                   ::K/key   key
                   ::K/value  value }
                  (fn callback [exception metadata]
                    (println (format "Record %s : %s"
                            key
                            (if exception
                              "FAILURE"
                              "SUCCESS")))))))

(defn send-n-times [n topic request] 
  (let [num (util/parse-int n)
        value (str (util/request-body-to-key-word request))]
    (for [x (range num)]
      (async-send topic (str (java.util.UUID/randomUUID)) value))))