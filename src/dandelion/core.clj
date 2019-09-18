(ns dandelion.core
  (:require [cheshire.core :as json])
  (:import (com.amazon.ion.system IonTextWriterBuilder
                                  IonReaderBuilder)))

(defn ion->json [ion-value]
  (let [stringBuilder (StringBuilder.)
        jsonWriter (.build (IonTextWriterBuilder/json) stringBuilder)
        reader (.build (IonReaderBuilder/standard) ion-value jsonWriter)]
    (.toString stringBuilder)))

(defn json->ion [^String json-value]
  (.build (IonReaderBuilder/standard) json-value))

(defn clj->ion [clj-value]
  (-> clj-value
      json/generate-string
      json->ion))

(defn ion->clj [ion-value]
;  (clj->ion clj-value {:key-fn identity}))
  (-> ion-value
      ion->json
      json/parse-string))
