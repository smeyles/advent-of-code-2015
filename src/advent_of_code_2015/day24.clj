(ns advent-of-code-2015.day24
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str])
  (:require [clojure.math.combinatorics :as combo]))

(defn parts [packages target]
  (loop [package-count 1]
    (let [solutions (->> (combo/combinations packages package-count)
                         (filter #(= target (reduce + %))))]
      (if (empty? solutions)
        (recur (inc package-count))
        solutions))))

(defn run []
  (let [input (str/split-lines (slurp (io/resource "day24.txt")))
        packages (map #(Integer/parseInt %) input)
        target (/ (reduce + packages) 4)
        candidates (parts packages target)]
    (reduce
      (fn [l' r] (let [r' (reduce * r)] (if (<= l' r') l' r')))
      (reduce * (first candidates))
      (rest candidates))))
