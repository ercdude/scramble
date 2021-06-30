(ns scramble-ui.events
  (:require
   [re-frame.core :as re-frame]
   [scramble-ui.db :as db]
   [day8.re-frame.http-fx]
   [ajax.core :as ajax]
   ))

;; TODO: how do I get this dynamically? This seems odd
(def api-url "http://ice.vms:3000/scramble")

(re-frame/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(re-frame/reg-event-fx
 ::change-dict
 (fn [{db :db} [_ value]]
   {:db (-> db
            (assoc :dict value)
            (dissoc :resp))}))

(re-frame/reg-event-fx
 ::change-word
 (fn [{db :db} [_ value]]
   {:db (-> db
            (assoc :word value)
            (dissoc :resp))}))

(re-frame/reg-event-fx
 ::submit-scramble
 (fn [{:keys [db]} _]
   (let [{:keys [dict word]} db]
     {:http-xhrio {:method          :get
                   :uri             (str api-url
                                         "?dict=" dict
                                         "&word=" word)
                   :timeout         1000
                   :response-format (ajax/text-response-format)
                   :on-success      [::scramble-success]
                   :on-failure      [::scramble-failure]}})))

(re-frame/reg-event-fx
 ::scramble-success
 (fn [{:keys [db]} [_ resp]]
   {:db (-> db (assoc :resp resp))}))

(re-frame/reg-event-fx
 ::scramble-failure
 (fn [{:keys [db]} [_ resp]]
   {:db (-> db (assoc :resp (str "error: " (get resp :status-text))))}))
