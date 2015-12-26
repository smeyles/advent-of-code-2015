(ns advent-of-code-2015.day18
  (:require [clojure.java.io :as io]))

(defn empty' [x] (= x \.))

(defn load' [lines]
  (reduce
    (fn [[y grid] line]
      (let [line' (reduce
                    (fn [[x y grid] c]
                      [(inc x) y (if (empty' c) grid (conj grid [x y]))])
                    [0 y grid]
                    line)]
        [(inc y) (nth line' 2)]))
    [0 #{}]
    lines))

(defn count-neighbours [[x y] grid]
  (let [x' (dec x) x'' (inc x) y' (dec y) y'' (inc y)]
    (count
      (filter
        #(some? %)
        (list (get grid [x' y'])  (get grid [x y'])  (get grid [x'' y'])
              (get grid [x' y])                      (get grid [x'' y])
              (get grid [x' y'']) (get grid [x y'']) (get grid [x'' y'']))))))

(defn next' [addr grid]
  (let [c (count-neighbours addr grid)]
    (if (some? (get grid addr))
      (case c (2 3) \# \.)   
      (if (= c 3) \# \.))))

(defn generate [[size grid]]
  [size
   (reduce
     (fn [g' addr] (let [c (next' addr grid)] (if (empty' c) g' (conj g' addr))))
     #{}
     (for [y (range size) x (range size)] [x y]))])

(defn stuck [[size grid]]
  (let [s' (dec size)]
  [size (apply conj grid (list [0 0] [0 s'] [s' 0] [s' s']))]))

(defn run []
  (with-open [rdr (io/reader (io/resource "day18.txt"))]
    (let [grid (stuck (load' (line-seq rdr)))
          f #(stuck (generate %))]
      (count (second (reduce (fn [g _] (f g)) grid (range 100)))))))
