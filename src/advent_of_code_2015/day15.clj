(ns advent-of-code-2015.day15
  (:require [clojure.java.io :as io]))

(def measures '(:capacity :durability :flavor :texture :calories))

(defn measure-score [allocs ingredients measure]
  (let [f (fn [[ingredient amount]] (* amount (get-in ingredients [ingredient measure])))]
    (->> allocs
         (map f)
         (reduce +)
         (max 0))))

(defn score [allocs ingredients]
  [
    (reduce
      *
      (map #(measure-score allocs ingredients %) (filter #(not= :calories %) measures)))
    (measure-score allocs ingredients :calories) 
  ])

(defn optimize
  ([capacity unresolved ingredients] (optimize capacity 0 {} unresolved ingredients))
  ([capacity alloced resolved unresolved ingredients]
   (let [i (first unresolved)]
     (if (= (count unresolved) 1)
       (let [r (assoc resolved i (- capacity alloced))
             s (score r ingredients)]
         ;(assoc r :score (score r ingredients))
         (if (= (second s)500) (first s) 0))
       (;flatten
        apply max
         (map
           #(optimize capacity % (assoc resolved i (- % alloced)) (rest unresolved) ingredients)
           (range alloced (inc capacity))))))))

(def re-ingredient
  #"([A-Za-z]+): capacity ([-0-9]+), durability ([-0-9]+), flavor ([-0-9]+), texture ([-0-9]+), calories ([-0-9]+)")

(defn load' [filename]
  (->> (slurp (io/resource filename))
       (re-seq re-ingredient)
       (reduce (fn [is [_ i c d f t cal]]
                 (assoc
                   is
                   i
                   {:capacity (Integer/parseInt c)
                    :durability (Integer/parseInt d)
                    :flavor (Integer/parseInt f)
                    :texture (Integer/parseInt t)
                    :calories (Integer/parseInt cal)}))
               {}))) 

(defn run []
  (let [ingredients (load' "day15.txt")]
    (optimize 100 (keys ingredients) ingredients)))
