(ns advent-of-code-2015.day14
  (:require [clojure.java.io :as io]))

(def r #"([A-Za-z]+) can fly (\d+) km/s for (\d+) seconds, but then must rest for (\d+) seconds.")

(defn dist' [time' [_ deer speed flying resting]]
  (let [speed' (Integer/parseInt speed)
        flying' (Integer/parseInt flying)
        resting' (Integer/parseInt resting)
        c (+ flying' resting')]
    [deer (+ (* (int (/ time' c)) flying' speed') (* speed' (min flying' (mod time' c))))]))

(defn run []
  (let [input (re-seq r (slurp (io/resource "day14.txt")))]
    (->> input
         (map #(second (dist' 2503 %)))
         (apply max))
    (->> (rest (reductions (fn [_ n] (map #(dist' n %) input)) {} (range 1 2504)))
         (map #(second (first (into (sorted-map-by >) (group-by (fn [[k v]] v) %)))))
         (map #(map first %))
         flatten
         frequencies)))
