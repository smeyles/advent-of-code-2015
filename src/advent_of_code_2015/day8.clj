(ns advent-of-code-2015.day8
  (:require [clojure.java.io :as io]))

(defn next' [s c]
  (let [transitions { :normal { \\ :escape :default :normal }
                      :escape { \x :x1 :default :normal }
                      :x1 { :default :x2 }
                      :x2 { :default :normal } }
        state-transitions (get transitions s)
        next-state (get state-transitions c)]
    (or next-state (get state-transitions :default))))

(defn overhead [l]
  (let [code-chars (count l)
        mem-chars (->> (reductions next' :normal l)
                       (filter (partial = :normal))
                       count)]
    (- code-chars (- mem-chars 3))))

(defn overhead' [l] (+ 2 (count (filter #(or (= % \\) (= % \")) l))))

(defn run []
  (with-open [rdr (io/reader (io/resource "day8.txt"))]
    (println (reduce + (map overhead' (line-seq rdr))))))

