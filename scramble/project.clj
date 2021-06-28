(defproject scramble "0.1.0-SNAPSHOT"
  :description "Scramble strings."
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 ;; Compojure - A basic routing library
                 [compojure "1.6.2"]
                 ;; Http library for client/server
                 [http-kit "2.5.3"]
                 ;; Ring defaults - for query params etc
                 [ring/ring-defaults "0.3.2"]]
  :main ^:skip-aot scramble.core
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
