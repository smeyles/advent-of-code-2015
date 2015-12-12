(ns advent-of-code-2015.day1
  (:require [clojure.java.io :as io]))

(defn run []
  (let [input (slurp (io/resource "day1.txt"))
        steps (map #(case %1 \( 1 \) -1 0) input)]
    (println (reduce + steps))
    (println
      (:count
        (reduce
          #(if (= (:floor %1) -1)
             (reduced %1)
             {:floor (+ (:floor %1) %2) :count (inc (:count %1))})
          {:floor 0 :count 0}
          steps)))))

