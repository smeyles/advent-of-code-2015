(ns advent-of-code-2015.day12
  (:require [clojure.java.io :as io])
  (:require [clojure.data.json :as json])) 

(defn no-red? [s]
  (not (some #{"red"} (vals s))))

(defn run []
  (let [input (json/read-str (slurp (io/resource "day12.txt")))]
    (reduce + (filter number? (tree-seq #(or (and (map? %) (no-red? %)) (vector? %)) identity input)))))


