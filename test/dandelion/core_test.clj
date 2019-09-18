(ns dandelion.core-test
  (:require [clojure.test :as t]
            [cheshire.core :as json]
            [dandelion.core :refer :all])
  (:import (com.amazon.ion IonType)))

(def sample-data {:text "some text"
                  :number 1234
                  :array [1, 2, 3, 4]
                  :obj {:more-array [5, 6, 7]
                        :more-number 789.3}
                  :bool true
                  :null nil})

(t/deftest dandelion-test
  (t/testing "Transformation"
    (t/testing "Clojure to Ion"
      (let [ion (clj->ion sample-data)]
        (t/is (= (.next ion) IonType/STRUCT) "Type should be STRUCT")

        (.stepIn ion)
        (.next ion)
        (t/is (= (.getFieldName ion) "text"))
        (t/is (= (.stringValue ion) (sample-data :text)))
        (.stepOut ion)))
    (t/testing "Ion to Clojure"
      (let [ion (clj->ion sample-data)]
        (t/is (= (ion->clj ion) sample-data))))))
