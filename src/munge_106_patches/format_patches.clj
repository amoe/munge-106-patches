(ns munge-106-patches.format-patches
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [clojure.pprint :as pprint]))


(declare roundscale)

(defn lfo-rate [n]
  (roundscale n 10))

(defn lfo-delay-time [n]
  (roundscale n 10))

(defn dco-lfo [n]
  (roundscale n 10))

(defn dco-pwm [n]
  (roundscale n 10))

(defn dco-noise [n]
  (roundscale n 10))

(defn vcf-freq [n]
  (roundscale n 10))

(defn vcf-res [n]
  (roundscale n 10))

(defn vcf-env [n]
  (roundscale n 10))

(defn vcf-lfo [n]
  (roundscale n 10))

(defn vcf-kybd [n]
  (roundscale n 10))

(defn vca-level [n]
  (roundscale n 10))

(defn env-a [n]
  (roundscale n 10))

(defn env-d [n]
  (roundscale n 10))

(defn env-s [n]
  (roundscale n 10))

(defn env-r [n]
  (roundscale n 10))

(defn dco-sub [n]
  (roundscale n 10))

; Ummm....
(defn bitmask-1 [n]
  {:range (cond (bit-test n 0)  "16'"
                (bit-test n 1)  "8'"
                (bit-test n 2)  "4'")
   :pulse? (bit-test n 3)
   :tri? (bit-test n 4)
   :chorus (if (bit-test n 5)
             (if (bit-test n 6) "1" "2")
             false)})
   

(defn bitmask-2 [n]
  {:pwm-mode (if (bit-test n 0) "MAN" "LFO")
   :env-mode (if (bit-test n 1) "GATE" "ENV")
   :polarity (if (bit-test n 2) "negative" "positive")
   :hpf (cond
         (and (not (bit-test n 3))
              (not (bit-test n 4)))  "3"
         (and (not (bit-test n 3))
              (bit-test n 4))        "2"
         (and (bit-test n 3)
              (not (bit-test n 4)))  "1"
         (and (bit-test n 3)
              (bit-test n 4))        "0")})
                 

(def sysex-controls
  [lfo-rate          ; index 0
   lfo-delay-time    ; index 1
   dco-lfo           ; index 2
   dco-pwm           ; index 3
   dco-noise         ; index 4
   vcf-freq          ; index 5
   vcf-res           ; index 6
   vcf-env           ; index 7
   vcf-lfo           ; index 8
   vcf-kybd          ; index 9
   vca-level         ; index 10
   env-a             ; index 11
   env-d             ; index 12
   env-s             ; index 13
   env-r             ; index 14
   dco-sub           ; index 15
   bitmask-1         ; index 16
   bitmask-2])       ; index 17

(defn read-data [path]
  (read-string (slurp path)))

(defn format-definition [definition]
  (map (fn [f x] (apply f [x]))    ; unary apply
       sysex-controls
       definition))

(defn format-data [data]
  (map
   (fn [patch]
     (update-in patch [:definition] format-definition))
   data))
  
(defn roundscale [n max]
  (format "%.2f" (double (* (/ n 127) max))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [data (read-data (first args))]
    (format-data data)))





