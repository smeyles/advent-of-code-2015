(ns advent-of-code-2015.day11
  (:require [clojure.java.io :as io]))

; character inc in [a-z] field
(defn c-inc [c]
  (let [limit (inc (int \z))
        next (inc (int c))]
    (if (= limit next) \a (char next))))

(defn s-inc [s]
  (let [r (fn [[carry-in r] c]
            (if carry-in
              (let [n (c-inc c)] [(= n \a) (conj r n)])
              [false (conj r c)]))]
    (->> (reverse s)
         (apply str)
         (reduce r [true '()])
         second
         (apply str))))

(def compliant? #"\A(?=.*(?:abc|bcd|cde|def|efg|fgh|ghi|hij|ijk|jkl|klm|lmn|mno|nop|opq|pqr|qrs|rst|stu|tuv|uvw|vwx|wxy|xyz))(?=^[^ilo]*$)(?:.*([a-z])\1){2}")

(defn run
  ([] (run "hepxcrrq"))
  ([current]
    (->> (iterate s-inc current)
         rest
         (filter #(re-find compliant? %))
         first)))
