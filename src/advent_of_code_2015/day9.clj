(ns advent-of-code-2015.day9
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [clojure.math.combinatorics :as combo]))

(defn add-distance [dt [_ from to dist]]
  (let [dist' (Integer/parseInt dist)]
    (assoc-in (assoc-in dt [from to] dist') [to from] dist')))

(defn calc-route-distance [lookup cities]
  (let [r (fn [[from dist] to] [to (+ dist (get-in lookup [from to]))])]
    (second (reduce r [(first cities) 0] (rest cities)))))

(defn calc-extreme [f lookup]
  (let [cities (keys lookup)]
    (apply f (map #(calc-route-distance lookup %) (combo/permutations cities)))))

(defn run []
  (->> (slurp (io/resource "day9.txt") )
       (re-seq #"([a-zA-Z]+) to ([a-zA-Z]+) = (\d+)")
       (reduce add-distance {})
       (calc-extreme max))) ; (a) min (b) max

