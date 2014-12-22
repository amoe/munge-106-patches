(ns munge-106-patches.core
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as str]))

(defn read-dump [dump-path]
  (with-open [r (io/reader dump-path)]
    (doall (line-seq r))))

(defn get-indices [dump]
  (keep-indexed #(when (re-matches #"\[instance 0x.{6}: 0x.{6}/Patch"
                                   %2)
                   %1)
                dump))

(defn munge-sysex-list [list-spec]
  (map read-string (str/split list-spec #", ")))

(defn parse-definition [definition-line]
  (let [[match group1] (re-find #"\[arraycoll sz 18 (.*)\]$" definition-line)]
    (munge-sysex-list group1)))

(defn parse-name [name-line]
  (let [[match group1] (re-find #"\"(.*)\"" name-line)]
    group1))

  
(defn  handle-patch [index dump]
  (let [patch-definition-line (nth dump (+ index 3))
        patch-name-line (nth dump (+ index 4))]
    {:definition (parse-definition patch-definition-line)
     :name (parse-name patch-name-line)}))

    


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [dump (read-dump (first args))]
    (map #(handle-patch % dump) (get-indices dump))))




