(ns tst.server
  (:require [tst.handler :refer [app]]
            [config.core :refer [env]]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(use 'noir.fetch.remotes)

(defremote adder [& nums]
           (apply + nums))

(defremote get-user [id]
           {:username "Chris"
            :age 24})

 (defn -main [& args]
   (let [port (Integer/parseInt (or (env :port) "3000"))]
     (run-jetty app {:port port :join? false})))
