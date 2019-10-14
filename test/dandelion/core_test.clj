(ns dandelion.core-test
  (:require [clojure.test :as t]
            [dandelion.core :refer [clj->ion ion->clj clj->ion-binary]])
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
      (t/is (= (ion->clj ion) sample-data))))

  (t/testing "Ion binary to Clojure"
    (let [ion-binary (clj->ion-binary sample-data)]
      (t/is (= (ion->clj ion-binary) sample-data)))))
