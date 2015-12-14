(ns advent-of-code-2015.day6
  (:require [clojure.java.io :as io])
  (:import java.lang.Integer)) 

(def re-parse #"(turn on|turn off|toggle) (\d+),(\d+) through (\d+),(\d+)")

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

(defn light [s grid [_ instr x1 y1 x2 y2]]
  (let [p #(Integer/parseInt %)
        lights (for [x (range (p x1) (inc (p x2)))
                     y (range (p y1) (inc (p y2)))] [x y])]
    (reduce (partial s instr) grid lights)))

(defn run []
  (let [grid (into [] (repeat 1000000 0))]
    (->> (slurp (io/resource "day6.txt"))
         (re-seq re-parse)
         (reduce (partial light switch') (transient grid))
         (persistent!)
         (reduce +))))

