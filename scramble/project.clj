(defproject scramble "0.1.0-SNAPSHOT"
  :url "https://https://gitlab.com/ercdude/scramble"
  :description "Scramble backend"
  :license {:name "MIT LICENSE" :url ""}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 ;; Compojure - A basic routing library
                 [compojure "1.6.2"]
                 ;; Http library for client/server
                 [http-kit "2.5.3"]
                 ;; Ring defaults - for query params etc
                 [ring/ring-defaults "0.3.2"]]
                 ;; benchmarks - measures the computation time of an expression
                 ;; [criterium "0.4.6"]]
  :main ^:skip-aot scramble.hello
  :target-path "target/%s"
  :profiles {:socket {:jvm-opts ["-Dclojure.server.repl={:name \"repl-server\"
                                               :port 5555
                                               :address \"0.0.0.0\"
                                               :accept clojure.core.server/repl
                                               :bind-err true
                                               :server-daemon false
                                               :client-daemon false}"]}
             :uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
