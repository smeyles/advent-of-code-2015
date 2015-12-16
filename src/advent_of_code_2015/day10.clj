(ns advent-of-code-2015.day10
  (:require [clojure.java.io :as io]))

(defn run []
  (let [las (fn [p _] (reduce #(str %1 (count %2) (first %2)) "" (partition-by identity p)))]
    (count (reduce las "1113222113" (range 50)))))

