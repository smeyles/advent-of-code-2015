(ns advent-of-code-2015.day4
  (:import java.security.MessageDigest))

(defn digest [algo message]
  (.update algo (.getBytes message))
  (.toString (BigInteger. 1 (.digest algo)) 16))

(defn run
  ([] (run "yzbqklnj" 0 26))
  ([prefix n len]
   (let [md5 (MessageDigest/getInstance "MD5")
         hash (digest md5 (format "%s%d" prefix n))]
     (if (= len (count hash))
       n
       (recur prefix (inc n) len)))))

