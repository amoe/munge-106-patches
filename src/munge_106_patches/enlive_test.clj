(ns munge-106-patches.write-table
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :as pprint]
            [net.cgrand.enlive-html :as html]))

(def template-string
  "<html><body><ul class='mylist'><li><span class='name'></span><span class='age'></span></ul></body></html>")


(html/deftemplate list-page (java.io.StringReader. template-string) [data]
  [:ul.mylist :li] (html/clone-for [item data]
                     [:span.name]  (html/content (:name item))
                     [:span.age]   (html/content (str (:age item)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, world"))

