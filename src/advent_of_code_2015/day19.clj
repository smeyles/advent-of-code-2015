(ns advent-of-code-2015.day19
  (:require [clojure.java.io :as io])
  (:require [clojure.string :as str]))

(defn re-seq' [m]
  (loop [replacements []]
    (if (re-find m)
      (recur (conj replacements {:value (.group m) :start (.start m) :end (.end m)}))
      replacements)))

(defn replace' [s r match]
  (str (subs s 0 (:start match)) r (subs s (:end match))))

(defn gen-one [molecule [_ from to]]
  (map
    (partial replace' molecule to)
    (re-seq' (re-matcher (re-pattern from) molecule))))

(defn gen-all [molecule repls]
  (->> repls
       (map (partial gen-one molecule))
       flatten
       (into #{})))

(defn gen-rev [molecule repls]
  (let [r (re-pattern (str/join "|" (keys repls)))]
    (loop [m molecule, count 0]
      (if (= m "e")
        count
        (when-let [match (last (re-seq' (re-matcher r m)))]
          (recur (replace' m (get repls (:value match)) match) (inc count)))))))

(defn run []
  (let [input (slurp (io/resource "day19.txt") )
        repls (re-seq #"(\w+) => (\w+)" input)
        molecule (last (str/split-lines input))
        repls' (reduce (fn [m [_ from to]] (assoc m to from)) {} repls)]

    ;(count (gen-all molecule repls))
    (gen-rev molecule repls')))
