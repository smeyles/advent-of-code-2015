(ns advent-of-code-2015.day4
  (:import java.security.MessageDigest))

(defn digest [algo message]
  (.update algo (.getBytes message))
  (.toString (BigInteger. 1 (.digest algo)) 16))

(defn run
  ([] (run "yzbqklnj" 26))
  ([prefix len]
   (let [md5 (MessageDigest/getInstance "MD5")
         hash #(digest md5 (format "%s%d" prefix %))]
     (first
       (for [n (range) :let [hash (digest md5 (format "%s%d" prefix n))]
                       :when (= len (count hash))]
         n))))) 
