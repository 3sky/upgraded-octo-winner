(ns clojure-raw-rest-api.core
  (:require [ring.adapter.jetty :as jetty]
    [ring.middleware.params :refer [wrap-params]]
    [ring.middleware.reload :refer [wrap-reload]]
    [ring.middleware.keyword-params :refer [wrap-keyword-params]]
    [ring.middleware.json :refer [wrap-json-params wrap-json-response]]
    [clojure.java.io :as io]
    [clj-http.client :as client])
  (:gen-class))

; Read enviroment variable
(def env (or (System/getenv "env") "dev"))

; Simple page
(defn simple-page [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "<h1>Hello World</h1>"})

; Return Health Check
(defn app-status [req]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body    {:status "ok"}})

; Return env(env var)
(defn enviroment-name [req]
  {:status  200
   :headers {"Content-Type" "text/json"}
   :body    {:enviroment env}})

; Return when no path
(defn missing-handler [request]
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body {:status "Error, path not found!"}})

(def routes [
    {:methods #{:get} :path "/" :handler simple-page}
    {:methods #{:get} :path "/status" :handler app-status}
    {:methods #{:get} :path "/env" :handler enviroment-name}
  ])

(defn route-match? [request route]
  (and ((:methods route) (:request-method request))
       (= (:path route) (:uri request))))

(defn app [request]
  (let [route (first (filter (partial route-match? request) routes))
        handler (get route :handler missing-handler)]
    (println "app request " (:request-method request) (:uri request) (pr-str route))
    (handler request)))

(defn with-middleware [handler]
  (-> handler
    (wrap-reload)
    (wrap-keyword-params)
    (wrap-json-params {})
    (wrap-params {})
    (wrap-json-response {:pretty true})))

(defn -main []
  (let [port (Integer. (or (System/getenv "PORT") 8081))]
    (jetty/run-jetty (with-middleware app) {:port port :join? false})))