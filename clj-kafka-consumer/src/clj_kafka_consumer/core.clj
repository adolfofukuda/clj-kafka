(ns clj-kafka-consumer.core
  (:require [dvlopt.kafka       :as K]
            [dvlopt.kafka.in    :as K.in]))

(defn async-consumer [topic]
  (loop []
    (with-open [consumer (K.in/consumer {::K/nodes              [["localhost" 9092]]
                                         ::K/deserializer.key   :string
                                         ::K/deserializer.value :string
                                         ::K.in/configuration   {"auto.offset.reset" "earliest"
                                                                 "enable.auto.commit" false
                                                                 "group.id"           "my-group"}})]
      (K.in/register-for consumer
                         [topic])
      (doseq [record (K.in/poll consumer
                                {::K/timeout [50 :seconds]})]
        (println (format "Record %d @%d - Key = %s, Value = %s"
                         (::K/offset record)
                         (::K/timestamp record)
                         (::K/key record)
                         (str (::K/value record)))))
      (K.in/commit-offsets consumer))
    (recur)))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (async-consumer "kafka-test"))

