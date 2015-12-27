(ns advent-of-code-2015.day23
  (:require [clojure.java.io :as io]))

(defn exec [instrs a]
  (loop [line 0, a a, b 0]
    (if-let [[_ i op1 op2] (get instrs line)]
      (case i
        "hlf" (recur
                (inc line)
                (if (= op1 "a") (bit-shift-right a 1) a)
                (if (= op1 "b") (bit-shift-right b 1) b))
        "tpl" (recur
                (inc line)
                (if (= op1 "a") (* 3 a) a)
                (if (= op1 "b") (* 3 b) b))
        "inc" (recur
                (inc line)
                (if (= op1 "a") (inc a) a)
                (if (= op1 "b") (inc b) b))
        "jmp" (recur
                (+ line (Integer/parseInt op1))
                a
                b)
        "jie" (let [even? #(= 0 (mod % 2))
                    op-even (if (= op1 "a") (even? a) (even? b))]
                (recur
                  (+ line (if op-even (Integer/parseInt op2) 1))
                  a
                  b))
        "jio" (let [op-one (= 1 (if (= op1 "a") a b))]
                (recur
                  (+ line (if op-one (Integer/parseInt op2) 1))
                  a
                  b)))
      {:a a :b b})))

(defn run []
  (let [instrs (into [] (re-seq
                          #"(\w+) ([-+a-z0-9]+)(?:, ([-+0-9]+))?"
                          (slurp (io/resource "day23.txt"))))]
    ; (exec instrs 0)
    (exec instrs 1)))
