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

(defn scramble-route
  "Handler for scramble route."
  [req]
  (let [dict (-> req :params :dict)
        word (-> req :params :word)]
    ;; print request for debugging
    (pp/pprint req)
    ;; send response
    (send-resp 200
               (if (and (false? (scramble/bad-char? dict))
                        (false? (scramble/bad-char? word)))
                 ;; dict and word valid. Check scramble.
                 (scramble/scramble? dict word)
                 ;; dict or word invalid. Return error
                 (str "Bad parameters. Dictionary and word must "
                      "be downcase letters")))))

(defroutes app-routes
  "Defines the allowed routes and its handlers."
  (GET "/scramble" [] scramble-route)
  (route/not-found "Not found..."))

(defn -main
  "Entry point for scramble backend"
  [& args]
  ;; bind port
  (let [port (Integer/parseInt (or (System/getenv "SCRAMBLE_PORT")
                                   "3000"))]
    ;; run the server with Ring.defaults middleware
    (server/run-server
     (wrap-defaults #'app-routes site-defaults) {:port port})
    ;; TODO: get the real IP
    (println (str "Running webserver at http://127.0.0.1:" port "/"))))

