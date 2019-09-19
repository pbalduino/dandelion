(ns dandelion.core
  (:require [clojure.data.json :as json])
  (:import (com.amazon.ion IonValue)
           (com.amazon.ion.system IonTextWriterBuilder
                                  IonReaderBuilder
                                  IonSystemBuilder)))

(defn ion->json
  "Transforms a IonValue to an JSON value serialised as String."
  [^IonValue ion-value]
  (let [ion-string (str ion-value)
        sb (StringBuilder.)
        writer (.build (.withJsonDowngrade (IonTextWriterBuilder/json)) sb)
        reader (.build (IonReaderBuilder/standard) ion-string)]
    (.writeValues writer reader)
    (str sb)))

(defn json->ion
  "Transforms a JSON value serialised as String to an immutable IonValue."
  [^String json-value]
  (let [system (.build (IonSystemBuilder/standard))
        value  (.singleValue system json-value)]
    (.makeReadOnly value)
    value))

(defn clj->ion
  "Transforms a Clojure value, usually a Map to an immutable IonValue."
  [clj-value]
  (-> clj-value
      json/write-str
      json->ion))

(defn ion->clj
  "Transforms a IonValue to a Clojure value"
  [ion-value]
  (-> ion-value
      ion->json
      json/read-str))
