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

(defn exact [litres sizes n]
  (= litres (mmult sizes (padl (count sizes) 0 (bit-stream n)))))

(defn run []
  (with-open [rdr (io/reader (io/resource "day17.txt"))]
    (let [sizes (map #(Integer/parseInt %) (line-seq rdr))]
      (count 
        (filter
          identity
          (map (partial exact 150 sizes) (range 1 (bit-shift-left 1 (count sizes)))))))))

