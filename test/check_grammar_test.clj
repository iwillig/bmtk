(ns check-grammar-test
  (:require [clojure.test :as t :refer [deftest is testing]]))

(deftest name-test
   (testing "Context of the test assertions"
     (is (= "" {}))))

(deftest second-test
  (testing "Context of the test assertions"
    (is (= {} {:test :value}))))
