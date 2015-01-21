(ns munge-106-patches.write-table
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :as pprint]
            [net.cgrand.enlive-html :as html]))

(html/deftemplate table-page "table.html" [table]
  [:table.mylist :tr.patch]  (html/clone-for [patch table]
                               [:td.name]  (html/content (:name patch))
                               [:td.definition]  (html/content (:definition patch))))
  
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (reduce str (table-page (first args))))

(defn countdown [n]
  (html/sniptest "<ul><li></li></ul>"
                 [:li] (html/clone-for [i (range n 0 -1)]
                                       (html/content (str i)))))
