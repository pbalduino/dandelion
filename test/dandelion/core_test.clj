(ns dandelion.core-test
  (:require [clojure.test :as t]
            [dandelion.core :refer [clj->ion ion->clj]])
  (:import (com.amazon.ion IonType)))

(def sample-data {"text" "some text"
                  "number" 1234
                  "array" [1, 2, 3, 4]
                  "obj" {"more-array" [5, 6, 7]
                         "more-number" 789.3}
                  "bool" true
                  "null" nil})

(t/deftest dandelion-test
  (t/testing "Transformation"
    (t/testing "Clojure to Ion"
      (let [ion (clj->ion sample-data)]
        (t/is (= (.getType ion) IonType/STRUCT) "Type should be STRUCT")

        (doseq [key (keys sample-data)]
          (t/is (.containsKey ion key) (str "Key " key " was not found"))))))

  (t/testing "Ion to Clojure"
    (let [ion (clj->ion sample-data)]
      (t/is (= (ion->clj ion) sample-data)))))

;; To transform Clojure data in Ion value
(-> sample-data ; some Clojure data
    clj->ion    ; where the magic happens
    str         ; convert to a readable string
    println)    ; show me the money

; {text:"some text",number:1234,array:[1,2,3,4], ↵
;  obj:{'more-array':[5,6,7],'more-number':789.3}, ↵
;  bool:true,'null':null}

;; To transform Ion value to Clojure data
(def ion-value (clj->ion sample-data))

(-> ion-value              ; Ion value
    ion->clj               ; simple, uh?
    clojure.pprint/pprint) ; welcome back
