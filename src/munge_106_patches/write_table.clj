(ns munge-106-patches.write-table
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :as pprint]
            [net.cgrand.enlive-html :as html]))

(defn format-numeric [patch key]
  (format "%.2f" (key (:definition patch))))

(defn format-boolean [patch key]
  (if (key (:definition patch))
    "&#x2713;"
    "&#x2717;"))


(html/deftemplate table-page "table.html" [table]
  [:table.mylist :tr.patch]
  (html/clone-for [patch table]
                  [:td.name]  (html/content (:name patch))
                  [:td.lfo-rate]
                  (html/content (format-numeric patch :lfo-rate))
                  
                  [:td.lfo-delay-time]
                  (html/content (format-numeric patch :lfo-delay-time))
                  [:td.range]
                  (html/content (:range (:definition patch)))
                  [:td.dco-lfo]
                  (html/content (format-numeric patch :dco-lfo))

                  [:td.dco-pwm]
                  (html/content (format-numeric patch :dco-pwm))

                  [:td.pwm-mode]
                  (html/content (:pwm-mode (:definition patch)))

                  [:td.pulse?]
                  (html/html-content (format-boolean patch :pulse?))

                  [:td.tri?]
                  (html/html-content (format-boolean patch :tri))

                  [:td.dco-sub]
                  (html/content (format-numeric patch :dco-pwm))

                  [:td.dco-noise]
                  (html/content (format-numeric patch :dco-noise))

                  [:td.hpf]
                  (html/content (:hpf (:definition patch)))

                  [:td.vcf-freq]
                  (html/content (format-numeric patch :vcf-freq))
                  [:td.vcf-res]
                  (html/content (format-numeric patch :vcf-res))
                  [:td.polarity]
                  (html/content (:polarity (:definition patch)))
                  [:td.vcf-env]
                  (html/content (format-numeric patch :vcf-env))
                  [:td.vcf-lfo]
                  (html/content (format-numeric patch :vcf-lfo))
                  [:td.vcf-kybd]
                  (html/content (format-numeric patch :vcf-kybd))

                  [:td.env-a]
                  (html/content (format-numeric patch :env-a))
                  [:td.env-d]
                  (html/content (format-numeric patch :env-d))
                  [:td.env-s]
                  (html/content (format-numeric patch :env-s))
                  [:td.env-r]
                  (html/content (format-numeric patch :env-r))

                  [:td.chorus]
                  (html/content (str (:chorus (:definition patch))))
))                  

                                               
  
(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (spit "out.html" (reduce str (table-page (first args)))))

(defn countdown [n]
  (html/sniptest "<ul><li></li></ul>"
                 [:li] (html/clone-for [i (range n 0 -1)]
                                       (html/content (str i)))))
