(ns advent-of-code-2015.day20
  (:require [clojure.java.io :as io])
  (:require [clojure.math.numeric-tower :as num])) 

(defn deliver' [mult upper bound]
  (let [upper' (inc upper)
        deliveries (for [elf (range 1 upper') , house (range elf (bound elf upper') elf)]
                     (vector elf house))]
    (persistent!
      (reduce
        (fn [r [elf house]] (assoc! r house (+ (* mult elf) (get r house))))
        (transient (vec (repeat upper' 0)))
        deliveries))))

(defn positions [pred coll]
  (keep-indexed (fn [idx x] (when (pred x) idx)) coll))

(def target 36000000)

(defn run []
  ;(let [presents 10
        ;bound (fn [elf upper] upper)]
    ;(first (positions (partial <= target) (deliver' presents (/ target presents) bound)))) 

  (let [presents 11
        upper (num/ceil (/ target 11))
        bound (fn [elf upper] (min upper (+ elf (* elf 50))))]
    (first (positions (partial <= target) (deliver' presents upper bound)))))
