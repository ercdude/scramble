(ns scramble-ui.subs
  (:require
   [re-frame.core :as re-frame]))

;; subscribe dict lambda function
(re-frame/reg-sub ::dict (fn [db] (:dict db)))

;; subscribe word lambda function
(re-frame/reg-sub ::word (fn [db] (:word db)))

;; subscribe response lambda function
(re-frame/reg-sub ::resp (fn [db] (:resp db)))
