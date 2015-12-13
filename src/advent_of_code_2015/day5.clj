(ns advent-of-code-2015.day5
  (:require [clojure.java.io :as io]))

(defn run
  ([] (run "day5.txt"))
  ([filename]
   (with-open [rdr (io/reader (io/resource filename))]
     (let [r #"\A(?=(?:[^aeiou]*[aeiou]){3})(?!.*(?:ab|cd|pq|xy)).*([a-z])\1"
           r' #"\A(?=.*([a-z][a-z]).*?\1).*([a-z])[a-z]\2" ]
       (count (filter #(re-find r' %) (line-seq rdr)))))))

