(ns clojure-raw-rest-api.core-test
  (:require [clojure.test :refer :all]
            [clojure-raw-rest-api.core :refer :all]
            [ring.mock.request :as mock]))

(deftest simple-page-test
  (is (= (simple-page (mock/request :get "/"))
         {:status  200
          :headers {"Content-Type" "text/html"}
          :body    "<h1>Hello World</h1>"})))
   
(deftest app-status-test
  (is (= (app-status (mock/request :get "/status"))
         {:status  200
         :headers {"Content-Type" "text/json"}
         :body    {:status "ok"}}))) 

(deftest enviroment-name-test
  (is (= (enviroment-name (mock/request :get "/env"))
         {:status  200
         :headers {"Content-Type" "text/json"}
         :body    {:enviroment "dev"}})))

(deftest enviroment-missing-handler
  (is (= (missing-handler (mock/request :get "/test"))
         {:status  404
         :headers {"Content-Type" "text/html"}
         :body {:status "Error, path not found!"}})))