(ns dandelion.core
  (:require [cheshire.core :as json])
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
        output (.newDatagram system)
        writer (.newWriter system output)
        value  (.singleValue system json-value)]
    (.makeReadOnly value)
    value))

(defn clj->ion [clj-value]
  (-> clj-value
      json/generate-string
      json->ion))

(defn ion->clj [ion-value]
  (-> ion-value
      ion->json
      json/parse-string))
