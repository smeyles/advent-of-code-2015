(ns advent-of-code-2015.day13
  (:require [clojure.java.io :as io])
  (:require [clojure.math.combinatorics :as combo]))

(defn add-happiness [dt [_ from losegain units to]]
  (let [units' (* (if (= "lose" losegain) -1 1) (Integer/parseInt units))]
    (assoc-in dt [from to] units')))

(defn calc-seating-happiness [lookup people]
  (let [f' (first people)
        happy (fn [d from to] (+ d (get-in lookup [from to]) (get-in lookup [to from])))
        r (fn [[from dist] to] [to (happy dist from to)])]
    (second (reduce r [f' (happy 0 f' (last people))] (rest people)))))

(defn calc-extreme [f lookup]
  (let [people (keys lookup)]
    (apply f (map #(calc-seating-happiness lookup %) (combo/permutations people)))))

(defn add-stephen [m]
  (let [people (keys m)]
    (assoc
      (into {} (map (fn [[k v]] [k (assoc v "Stephen" 0)]) m)) 
      "Stephen"
      (zipmap people (repeat (count people) 0)))))

(defn run []
  (->> (slurp (io/resource "day13.txt") )
       (re-seq #"([a-zA-Z]+) would (gain|lose) (\d+) happiness units by sitting next to ([a-zA-Z]+).")
       (reduce add-happiness {})
       add-stephen
       (calc-extreme max))) ; (a) min (b) max
