(ns advent-of-code-2015.day3
  (:require [clojure.java.io :as io])
  (:require [clojure.set :as set]))

(defn offset [c]
  ( case c
    \> {:x 1 :y 0}
    \< {:x -1 :y 0}
    \^ {:x 0 :y 1}
    \v {:x 0 :y -1}
    {:x 0 :y 0}))

(defn deliver' [positions c]
  (let [move (offset c) head (first positions)]
    (conj positions {:x (+ (:x move) (:x head)) :y (+ (:y move) (:y head))})))

; bah - couldn't find/discover take-nth
(defn uninterleave-n
  ([coll]
    (uninterleave-n coll 2 0))
  ([coll freq]
    (uninterleave-n coll freq 0))
  ([coll freq offset]
    (lazy-seq
      (when-let [s (seq coll)]
        (let [f (first s) r (rest s) modinc #(mod (inc %) freq)]
          (if (= 0 (mod offset freq))
            (cons f (uninterleave-n r freq (modinc offset)))
            (uninterleave-n r freq (modinc offset))))))))

(defn run
  ([] (run "day3.txt")) 
  ([filename]
   (let [input (slurp (io/resource filename))
         houses #(into #{} (reduce deliver' '({:x 0 :y 0}) %))]
     (count (houses input))
     (let [santa (houses (uninterleave-n input))
           robo (houses (uninterleave-n (rest input)))]
       (- (+ (count santa) (count robo)) (count (set/intersection santa robo)))))))

