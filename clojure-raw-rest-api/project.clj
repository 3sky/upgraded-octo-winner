(defproject clojure-raw-rest-api "1.0.0"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
    [ring/ring-jetty-adapter "1.4.0"]
    [ring/ring-json "0.4.0"]
    [ring/ring-devel "1.4.0"]
    [clj-http "2.2.0"]
    [ring/ring-mock "0.4.0"]]
  :main ^:skip-aot clojure-raw-rest-api.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
