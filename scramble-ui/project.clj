(defproject scramble-ui "0.1.0-SNAPSHOT"
  :min-lein-version "2.9.0"
  :url "https://https://github.com/ercdude/scramble"
  :description "Scramble frontend (ui)"
  :license {:name "MIT LICENSE" :url ""}
  :dependencies [[org.clojure/clojure "1.10.3"]
                 [org.clojure/clojurescript "1.10.773"]
                 [reagent "1.0.0"]
                 [re-frame "1.2.0"]
                 [day8.re-frame/http-fx "0.2.3"]]
  :plugins [[lein-cljsbuild "1.1.8"]]
  :source-paths ["src/clj" "src/cljs" "src/scramble_ui"]
  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]
  :profiles {:dev {} :prod {}}
  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/scramble_ui"]
     :figwheel     {:on-jsload "scramble-ui.core/mount-root"}
     :compiler     {:main                 scramble-ui.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :closure-defines      {goog.DEBUG true}
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true}}

    ;; {:id           "min"
    ;;  :source-paths ["src/cljs"]
    ;;  :compiler     {:main            scramble-ui.core
    ;;                 :output-to       "resources/public/js/compiled/app.js"
    ;;                 :optimizations   :advanced
    ;;                 :closure-defines {goog.DEBUG false}
    ;;                 :pretty-print    false}}
    ]})
