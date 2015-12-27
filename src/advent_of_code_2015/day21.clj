(ns advent-of-code-2015.day21
  (:require [clojure.java.io :as io])
  (:require [clojure.math.combinatorics :as math]))

(def weapons {"Dagger"     [ 8 4 0]
              "Shortsword" [10 5 0]
              "Warhammer"  [25 6 0]
              "Longsword"  [40 7 0] 
              "Greataxe"   [74 8 0]})

(def armors {"None"       [  0 0 0]
             "Leather"    [ 13 0 1]
             "Chainmail"  [ 31 0 2]
             "Splintmail" [ 53 0 3]
             "Bandedmail" [ 75 0 4]
             "Platemail"  [102 0 5]})

(def rings {"Damage +1"  [ 25 1 0]
            "Damage +2"  [ 50 2 0]
            "Damage +3"  [100 3 0]
            "Defense +1" [ 20 0 1]
            "Defense +2" [ 40 0 2]
            "Defense +3" [ 80 0 3]})

(def boss [109 8 2])
(def my-health 100)

(defn combinations' [items n]
  (mapcat (partial math/combinations items) (range (inc n))))

(defn win? [[my-points my-damage my-defense]
            [boss-points boss-damage boss-defense]]
  (let [my-net (- my-damage boss-defense)
        boss-net (- boss-damage my-defense)]
    (loop [mp my-points bp boss-points]
      (let [winner? (neg? bp)]
        (if (or (neg? mp) winner?)
          winner?
          (recur (- mp boss-net) (- bp my-net)))))))

(defn run []
  (let [r (->> (for [weapon (vals weapons)
                     armor (vals armors)
                     ring (combinations' (vals rings) 2)]
                 (concat (vector weapon armor) ring))
               (map
                 #(let [p (reduce (fn [r i] (map + r i)) [0 0 0] %)]
                    {:cost (first p)
                     :win (win? (vector my-health (second p) (last p)) boss)})))]
      ; (->> r (filter :win) (map :cost) (apply min))
      (->> r (filter (complement :win)) (map :cost) (apply max))
    ))
