(ns scramble.core
  (:require [org.httpkit.server :as server]
            [compojure.core :refer :all]
            [compojure.route :as route]
            [clojure.pprint :as pp]
            [ring.middleware.defaults :refer :all]
            [scramble.scramble :as scramble]))

(defn send-resp
  "Parse and return the final response."
  [status body]
  {:status  status
   :headers {"Content-Type" "text/html"
             "Access-Control-Allow-Origin" "*"
             "Access-Control-Allow-Headers" "Content-Type"}
   :body (str body)})

(defn index-route
  "Index route blablaba"
  [req]
  (pp/pprint req)
  (send-resp 200 "Works"))

(defn scramble-route
  "Handler for scramble route."
  [req]
  (let [dict (-> req :params :dict)
        target (-> req :params :target)]
    (pp/pprint req)
    (send-resp 200
               (if (and (false? (scramble/bad-char? dict))
                        (false? (scramble/bad-char? target)))
                 ;; dict and target valid. Check scramble.
                 (scramble/scramble? dict target)
                 ;; dict or target invalid. Return error
                 (str "Bad parameters. Dictionary and target must "
                      "be downcase letters")))))

(defroutes app-routes
  "Routes..."
  (GET "/" [] index-route)
  (GET "/scramble" [] scramble-route)
  (route/not-found "Not found..."))

(defn -main
  "Entry point for scramble backend"
  [& args]
  ;; bind port
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    ;; run the server with Ring.defaults middleware
    (server/run-server
     (wrap-defaults #'app-routes site-defaults) {:port port})
    ;; show/logs
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))

