(defproject pbalduino/dandelion "0.0.1"
  :description "A Clojure wrapper for Amazon Ion"
  :url "http://github.com/pbalduino/dandelion"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :scm {:name "git"
        :url "https://github.com/pbalduino/dandelion.git"}
  :source-paths ["src"]
  :deploy-repositories [["clojars" {:url "https://repo.clojars.org"}
                        ["releases" :clojars]
                        ["snapshots" :clojars]]]
  :dependencies [[cheshire "5.9.0" :exclusions [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor
                                                com.fasterxml.jackson.dataformat/jackson-dataformat-smile]]
                 [com.fasterxml.jackson.core/jackson-core "2.10.0.pr3"]
                 [com.fasterxml.jackson.core/jackson-databind "2.10.0.pr3"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-smile "2.10.0.pr3"]
                 [com.fasterxml.jackson.dataformat/jackson-dataformat-cbor "2.10.0.pr3"]
                 [com.amazon.ion/ion-java "1.5.0"]]
  :plugins [[lein-cljfmt "0.6.4"]]
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[clj-kondo "RELEASE"]
                                  [org.clojure/clojure "1.10.0"]
                                  [camel-snake-kebab "0.4.0"]]
                   :aliases {"clj-kondo" ["run" "-m" "clj-kondo.main"]}}})
