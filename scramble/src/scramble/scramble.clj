(ns scramble.scramble)

(defn occur<=?
  "Verfies if the value of KEY from MAP is equal or bigger than COUNT."
  [map key count]
  (let [val (get map key)]
    ;; returns false if val is nil, otherwise make another test
    (if (nil? val)
      false
      ;; test if val is bigger or equal to count
      (<= count val))))

(defn bad-char?
  "Check if WORD has any non-letter or uppercase character."
  [word]
  (let [regexp (re-pattern "[a-z]+")]
    (nil? (re-matches regexp word))))

(defn scramble?
  "Check if a portion of DICT can be rearranged to match TARGET."
  [dict target]
  (let [dict-items (frequencies dict)
        target-items (frequencies target)
        ocurrencies (map (fn [x] (occur<=? dict-items
                                           (first x)
                                           (last x)))
                         target-items)]
    (empty? (filter false? ocurrencies))))

;; REPL debuging
;; (scramble? "test" "testttt")
;;
;; (let [dict "blabla"
;;       target "blablala"
;;       dict-items (frequencies dict)
;;       target-items (frequencies target)
;;       target-count (count target-items)]
;;   (map (fn [x] (occur<=? dict-items
;;                          (first x)
;;                          (last x)))
;;        target-items))
