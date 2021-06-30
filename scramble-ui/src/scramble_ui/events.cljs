(ns scramble-ui.events
  (:require
   [re-frame.core :as re-frame]
   [scramble-ui.db :as db]
   [day8.re-frame.http-fx]
   [ajax.core :as ajax]
   ))

;; Defines the api port for sending the AJAX.
(def api-port "3000")

;; Defines the api host for sending the AJAX.
(def api-host "ice.vms")

;; Defines the api url, based on API-HOST and API-PORT, for sending the AJAX.
(def api-url (str "http://" api-host ":" api-port "/scramble"))

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
