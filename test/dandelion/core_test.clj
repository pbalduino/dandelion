(ns dandelion.core-test
  (:require [clojure.test :as t]
            [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [dandelion.core :refer [clj->ion ion->clj]]))

(def simple-type-gen
  (gen/one-of [gen/int
               gen/size-bounded-bigint
               (gen/double* {:infinite? false :NaN? false})
               gen/string
               gen/boolean]))

(defn container-type
  [inner-type]
  (gen/one-of [(gen/vector inner-type)
           ;; scaling this by half since it naturally generates twice
           ;; as many elements
           (gen/scale #(quot % 2)
                  (gen/map (gen/one-of [gen/keyword gen/keyword-ns]) inner-type))]))

(defspec round-trip 100
  (prop/for-all [v (gen/recursive-gen container-type simple-type-gen)]
                (t/is (= v (-> v clj->ion (ion->clj true))))))
