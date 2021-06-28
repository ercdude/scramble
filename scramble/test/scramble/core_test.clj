(ns scramble.core-test
  (:require [clojure.test :refer :all]
            [scramble.scramble :refer :all]))

(deftest scramble-test
  (testing "scramble? test"
    (is (= true (and (scramble? "cedewaraaossoqqyt" "codewars")
                     (scramble? "rekqodlw" "world"))))))

(deftest scramble-fail-test
  (testing "scramble? test"
    (is (= false (scramble? "katas"  "steak")))))

(deftest badchar-test
  (testing "bad-char? test"
    (is (= false (bad-char? "asdwe")))))

(deftest badchar-fail-test
  (testing "bad-char? fail test"
    (is (= true (bad-char? "asd2we")))))
