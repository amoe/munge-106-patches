(ns munge-106-patches.write-table
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :as pprint]
            [net.cgrand.enlive-html :as html]))

(html/deftemplate table-page "table.html" [table]
  [:span.somekey] (html/clone-for [i (range 10 0 -1)]
                                  (html/content (str i))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (reduce str (table-page {:somekey "WHAT?"})))

(defn countdown [n]
  (html/sniptest "<ul><li></li></ul>"
                 [:li] (html/clone-for [i (range n 0 -1)]
                                       (html/content (str i)))))
