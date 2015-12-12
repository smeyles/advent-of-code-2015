(ns advent-of-code-2015.day2
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [clojure.math.combinatorics :as math])
  (:import java.lang.Integer))

(defn areas [[l w h]]
  (list (* l w) (* l h) (* w h)))

(defn wrapping_needed [la]
  (+ (* 2 (reduce + la)) (apply min la)))

(defn ribbon_needed [ld]
  (+
    (* 2 (- (reduce + ld) (apply max ld)))
    (reduce * ld)))

(defn run []
  (with-open [rdr (io/reader (io/resource "day2.txt"))]
    (let [parse #(Integer/parseInt %)
          dimensions (map (partial map parse) (map #(str/split %1 #"x") (line-seq rdr))) ]
      (println (reduce + (map wrapping_needed (map areas dimensions))))
      (println (reduce + (map ribbon_needed dimensions))))))

