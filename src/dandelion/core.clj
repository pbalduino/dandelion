(ns dandelion.core
  (:require [clojure.data.json :as json])
  (:import (com.amazon.ion.system IonTextWriterBuilder
                                  IonReaderBuilder
                                  IonSystemBuilder)))

(defn ion->json [ion-value]
  (let [ion-string (str ion-value)
        sb (StringBuilder.)
        writer (.build (.withJsonDowngrade (IonTextWriterBuilder/json)) sb)
        reader (.build (IonReaderBuilder/standard) ion-string)]
    (.writeValues writer reader)
    (str sb)))

(defn json->ion [^String json-value]
  (let [system (.build (IonSystemBuilder/standard))
        value  (.singleValue system json-value)]
    (.makeReadOnly value)
    value))

(defn clj->ion [clj-value]
  (-> clj-value
      json/write-str
      json->ion))

(defn ion->clj [ion-value]
  (-> ion-value
      ion->json
      json/read-str))
