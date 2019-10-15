(defproject pbalduino/dandelion "0.1.2"
  :description "A Clojure wrapper for Amazon Ion"
  :url "http://github.com/pbalduino/dandelion"
  :license {:name "MIT License"
            :url "https://opensource.org/licenses/MIT"}
  :scm {:name "git"
        :url "https://github.com/pbalduino/dandelion"}
  :source-paths ["src"]
  :deploy-repositories [["clojars" {:url "https://repo.clojars.org"}
                        ["releases" :clojars]
                        ["snapshots" :clojars]]]
  :dependencies [[org.clojure/data.json "0.2.6"]
                 [com.amazon.ion/ion-java "1.5.0"]]
  :plugins [[lein-cljfmt "0.6.4"]
            [lein-cloverage "1.1.2"]]
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[clj-kondo "RELEASE"]
                                  [org.clojure/clojure "1.10.1"]]
                   :aliases {"clj-kondo" ["run" "-m" "clj-kondo.main"]}}})
