(ns scramble-ui.views
  (:require
   [re-frame.core :as re-frame]
   [scramble-ui.subs :as subs]
   [scramble-ui.events :as events]
   [clojure.pprint :as pp]
   ))

(defn- target-value [event]
  (-> event
      (.-target)
      (.-value)))

(defn- event-dispatch [event-name & value-flag]
  "Dispatch EVENT-NAME using VALUE-FLAG."
  (fn [event]
    (re-frame/dispatch [event-name (and value-flag (target-value event))])))

(defn main-panel []
  "Construct the main form."
  (let [resp (re-frame/subscribe [::subs/resp])
        word (re-frame/subscribe [::subs/word])
        dict (re-frame/subscribe [::subs/dict])]
    [:div
     [:form
      ;; TODO: when using label it doesn't became visible, why?
      "Dict:"
      [:input {:type "text"
               :value @dict
               :on-change (event-dispatch ::events/change-dict
                                          'value)}]
      [:br]
      ;; TODO: when using label it doesn't became visible, why?
      "Word:"
      [:input {:type "text"
               :value @word
               :on-change (event-dispatch ::events/change-word
                                          'value)}]
      [:br]
      [:input {:type "button"
               :value "Scramble"
               :on-click (event-dispatch ::events/submit-scramble)}]
      [:br]
      [:span " " @resp]]]))
