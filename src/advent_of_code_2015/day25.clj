(ns advent-of-code-2015.day25)

(def row 2978)
(def col 3083)

(defn run []
  (loop [r 6, c 2, n 6796745]
    (if (and (= r row) (= c col))
      n
      (recur
        (if (= r 1) (inc c) (dec r))
        (if (= r 1) 1 (inc c))
        (rem (* n 252533) 33554393)))))
