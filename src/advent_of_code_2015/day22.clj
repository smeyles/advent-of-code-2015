(ns advent-of-code-2015.day22
  (:require [clojure.set :as set]))

(def boss {:health 71 :damage 10})
(def wiz {:health 50 :mana 500 :armour 0})
(def LOSER Integer/MAX_VALUE)

(def spells {"Magic Missile" {:cost 53  :duration 1 :damage 4          }
             "Drain"         {:cost 73  :duration 1 :damage 2 :health 2}
             "Shield"        {:cost 113 :duration 6           :armour 7}
             "Poison"        {:cost 173 :duration 6 :damage 3          }
             "Recharge"      {:cost 229 :duration 5           :mana 101}})

(def spell-keys (into (sorted-set-by #(compare %2 %1)) (keys spells)))

(defn apply-spells [boss wiz casts depth]
  (loop [b boss, w wiz, cast (first casts), r (rest casts)]
    (if (nil? cast)
      (vector b w (reduce (fn [cs [spell expiry]]
                            (if (= depth expiry) (dissoc cs spell) cs)) casts casts))
      (let [[spell expiry] cast
            attrs (get spells spell)
            attr-armour (:armour attrs 0)
            b' (update b :health - (:damage attrs 0))
            w' (-> (if (and (not= 0 attr-armour) (= depth expiry)) (update w :armour - attr-armour) w)
                   (update :health + (:health attrs 0))
                   (update :mana + (:mana attrs 0)))]
        (recur b' w' (first r) (rest r))))))

(defn boss-attack [boss wiz]
  (let [net-damage (max 1 (- (:damage boss) (:armour wiz)))]
    (assoc wiz :health (- (:health wiz) net-damage))))

(defn next-casts [wiz casts spent limit depth]
  (let [mana-to-spend (min (- limit spent 1) (:mana wiz))]
    (->> (set/difference spell-keys (into #{} (keys casts)))
         (map
           #(let [attrs (get spells %)]
             {:spell %, :cost (:cost attrs), :duration (:duration attrs), :armour (:armour attrs 0)}))
         (filter #(>= mana-to-spend (:cost %)))
         (map #(vector
                (assoc casts (:spell %) (+ depth (:duration %)))
                (+ spent (:cost %))
                (-> wiz
                    (update :mana - (:cost %))
                    (update :armour + (:armour %))))))))

(defn sim
  ([boss wiz hard] (sim boss wiz LOSER {} 0 0 true hard))
  ([boss wiz best casts depth spent players-turn hard]
   (let [wiz (if (and hard players-turn) (update wiz :health - 1) wiz)]
     (if (= 0 (:health wiz))
       LOSER
       (let [[boss' wiz' casts'] (apply-spells boss wiz casts depth)
             lose? #(<= (:health %) 0)]
         (cond
           (lose? boss') spent
           players-turn (apply
                          min
                          best
                          (map
                            (fn [[casts'' spent' wiz'']]
                              (sim boss' wiz'' best casts'' (inc depth) spent' false hard)) 
                            (next-casts wiz' casts' spent best depth)))
           ; boss' turn
           true (let [wiz'' (boss-attack boss' wiz')]
                  (if (lose? wiz'')
                    LOSER
                    (sim boss' wiz'' best casts' (inc depth) spent true hard)))))))))

(defn run []
  (sim boss wiz false) 
  (sim boss wiz true))

