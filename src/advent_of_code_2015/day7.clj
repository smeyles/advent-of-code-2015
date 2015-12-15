(ns advent-of-code-2015.day7
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str]))

(def b16 #(bit-and 2r1111111111111111 %))

(defn AND [a b] (b16 (bit-and a b)))
(defn OR [a b] (b16 (bit-or a b)))
(defn LSHIFT [a b] (b16 (bit-shift-left a b)))
(defn RSHIFT [a b] (b16 (bit-shift-right a b)))
(defn NOT [a] (b16 (bit-not a)))

(defn d7-fn [s]
  (if (symbol? s) `(~s) `(identity ~s)))

(defn d7-decl
  ([df s] (if (symbol? s) `(do (declare ~s) ~df) df))
  ([coll s1 s2] (d7-decl (d7-decl coll s1) s2)))

(defmacro d7
  ([op1 arrow w]
    (d7-decl (list 'def w (list 'memoize `(fn [] ~(d7-fn op1)))) op1))
  ([op op1 arrow w]
    (d7-decl (list 'def w (list 'memoize (list 'fn [] `(~op ~(d7-fn op1))))) op1))
  ([op1 op op2 arrow w]
    (d7-decl
      (list 'def w (list 'memoize
                     (list 'fn [] `(~op ~(d7-fn op1) ~(d7-fn op2)))))
      op1
      op2)))

(defn run
  ([] (run "day7.txt"))
  ([filename]
   (with-open [rdr (io/reader (io/resource filename))]
     (let [r' #(str/replace % #"if|do|fn" {"if" "if*" "fn" "fm*" "do" "do*"})]
       (doseq [d7-op (->> (line-seq rdr)
                          (map #(read-string (str "(d7 " (r' %) ")"))))]
         (eval d7-op))))))
