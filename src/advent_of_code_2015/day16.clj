(ns advent-of-code-2015.day16
  (:require [clojure.java.io :as io]))

; this is technically a cheat since description doesn't constrain input to
; exactly three items per sue (but my fixed input is such so...)
(def re-sue #"Sue (\d+): ([a-z]+): ([-0-9]+), ([a-z]+): ([-0-9]+), ([a-z]+): ([-0-9]+)")

(defn load' [filename]
  (->> (slurp (io/resource filename))
       (re-seq re-sue)
       (reduce (fn [sues [_ num i1 c1 i2 c2 i3 c3]]
                 (assoc
                   sues
                   num
                   {i1 (Integer/parseInt c1)
                    i2 (Integer/parseInt c2)
                    i3 (Integer/parseInt c3)}))
               {}))) 

(def memory {"children" 3
             "cats" 7
             "samoyeds" 2
             "pomeranians" 3
             "akitas" 0
             "vizslas" 0
             "goldfish" 5
             "trees" 3
             "cars" 2
             "perfumes" 1})

(defn match? [m n item]
  (case item
    ("cat" "trees") (> m n)
    ("pomeranians" "goldfish") (< m n)
    (= m n)))

(defn match [[s items]]
  (reduce (fn [r [item count]] (and r (match? count (get memory item) item))) true items))

(defn run []
  (filter match (load' "day16.txt")))
