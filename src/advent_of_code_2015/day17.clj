(ns advent-of-code-2015.day17
  (:require [clojure.java.io :as io]))

(defn bit-stream [n]
  (loop [s '()
         n' n]
    (if (= 0 n')
      s
      (recur
        (conj s (bit-and n' 1))
        (bit-shift-right n' 1)))))

(defn padl [bits x s]
  (concat (repeat (- bits (count s)) x) s))

;to-do - find standard matrix fns
(defn mmult [s t]
  (loop [n 0
         s' (seq s)
         t' (seq t)]
    (if (and s' t')
      (recur (+ n (* (first s') (first t')))
             (next s')
             (next t'))
      n)))

(defn bottles [s]
  (count (filter #(= 1 %) s)))

(defn exact [litres sizes n]
  (let [b (bit-stream n)]
  [(bottles b) (= litres (mmult sizes (padl (count sizes) 0 b)))]))

(defn run []
  (with-open [rdr (io/reader (io/resource "day17.txt"))]
    (let [sizes (map #(Integer/parseInt %) (line-seq rdr))]
      (reduce
        (fn [r [bottles _]] (assoc r bottles (inc (get r bottles 0))))
        (into (sorted-map) {}) 
        (filter
          (fn [[bottles exact-fit]] exact-fit)
          (map (partial exact 150 sizes) (range 1 (bit-shift-left 1 (count sizes)))))))))

