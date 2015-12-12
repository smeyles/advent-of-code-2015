(ns advent-of-code-2015.day3
  (:require [clojure.java.io :as io])
  (:require [clojure.set :as set]))

(defn offset [c]
  ( case c
    \> {:x 1 :y 0}
    \< {:x -1 :y 0}
    \^ {:x 0 :y 1}
    \v {:x 0 :y -1}
    {:x 0 :y 0}))

(defn deliver' [positions c]
  (let [move (offset c) head (first positions)]
    (conj positions {:x (+ (:x move) (:x head)) :y (+ (:y move) (:y head))})))

(defn run []
  (let [input (slurp (io/resource "day3.txt"))
        houses #(into #{} (reduce deliver' '({:x 0 :y 0}) %))]
    (count (houses input))
    (let [santa (houses (take-nth 2 input))
          robo (houses (take-nth 2 (rest input)))]
      (count (set/union santa robo)))))

