(ns advent-of-code-2015.day10
  (:require [clojure.java.io :as io]))

(defn run []
  (let [las (fn [p _] (->> (partition-by identity p)
                           (map #(identity [(count %) (first %)]))
                           flatten
                           (apply str)))]
    (count (reduce las "1113222113" (range 50)))))

