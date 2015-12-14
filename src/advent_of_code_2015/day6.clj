(ns advent-of-code-2015.day6
  (:require [clojure.java.io :as io])
  (:import java.lang.Integer)) 

(def re-parse #"(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)")

(defn parse [l]
  (let [matcher (re-matcher re-parse l)]
    (if (re-find matcher)
      (let [g (re-groups matcher)]
        (conj (map #(Integer/parseInt %) (drop 2 g)) (get g 1)))
      nil)))

(defn switch [instr grid [x y]]
  (let [n (+ x (* 1000 y))]
  (case instr
    "turn on" (assoc! grid n 1)
    "turn off" (assoc! grid n 0)
    "toggle" (assoc! grid n (- 1 (get grid n))))))

(defn switch' [instr grid [x y]]
  (let [n (+ x (* 1000 y))]
  (case instr
    "turn on" (assoc! grid n (inc (get grid n)))
    "turn off" (assoc! grid n (max 0 (dec (get grid n))))
    "toggle" (assoc! grid n (+ 2 (get grid n))))))

(defn light [s grid [instr x1 y1 x2 y2]]
  (let [lights (for [x (range x1 (inc x2)) y (range y1 (inc y2))] [x y])]
    (reduce (partial s instr) grid lights)))

(defn run []
  (with-open [rdr (io/reader (io/resource "day6.txt"))]
    (let [grid (into [] (repeat 1000000 0))]
      ;(->> (line-seq rdr)
           ;(map parse)
           ;(reduce (partial light switch) (transient grid))
           ;(persistent!)
           ;(reduce +))
      (->> (line-seq rdr)
           (map parse)
           (reduce (partial light switch') (transient grid))
           (persistent!)
           (reduce +)))))

