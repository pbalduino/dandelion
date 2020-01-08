(ns dandelion.core
  (:require [clojure.data.json :as json])
  (:import [com.amazon.ion.system
            IonBinaryWriterBuilder
            IonReaderBuilder
            IonSystemBuilder
            IonTextWriterBuilder]
           java.io.ByteArrayOutputStream))

(defn- preserve-ns [key]
  (if (keyword? key)
    (if-let [namespace (namespace key)] (str namespace "/" (name key)) (name key))
    (str key)))

(defn ion->json
  "Transforms an IonValue to a JSON value serialised as String."
  [ion-value]
  (let [sb (StringBuilder.)
        writer (.build (.withJsonDowngrade (IonTextWriterBuilder/json)) sb)
        reader (.build (IonReaderBuilder/standard) ion-value)]
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
      (json/write-str :key-fn preserve-ns)
      json->ion))

(defn clj->ion-binary
  "Transforms a Clojure value to a byte array of Ion binary"
  [clj-value]
  (let [ion-value (clj->ion clj-value)]
    (with-open [os (ByteArrayOutputStream.)
                writer (.build (IonBinaryWriterBuilder/standard) os)]
      (.writeTo ion-value writer)
      (.finish writer)
      (.toByteArray os))))

(defn ion->clj
  "Transforms a IonValue to a Clojure value"
  ([ion-value]
   (ion->clj ion-value false))
  ([ion-value keywordize?]
   (-> ion-value
       ion->json
       (json/read-str :key-fn (if keywordize? keyword identity)))))
