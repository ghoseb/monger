(ns monger.test.factory-dsl
  (:use     [clojure.test]
            [monger.testing]
            [clj-time.core :only [days ago weeks]])
  (:import [org.bson.types ObjectId]))

(defaults-for "domains"
  :ipv6-enabled false)

(factory "domains" "clojure.org"
         :name       "clojure.org"
         :created-at (-> 2 days ago))

(deftest test-building-documents-from-a-factory-case-1
  (let [t   (-> 2 weeks ago)
        doc (build "domains" "clojure.org" :created-at t)]
    (is (:_id doc))
    (is (= t (:created-at doc)))
    (is (= "clojure.org" (:name doc)))
    (is (false? (:ipv6-enabled doc)))))

(deftest test-building-documents-from-a-factory-case-2
  (let [oid (ObjectId.)
        doc (build "domains" "clojure.org" :_id oid)]
    (is (= oid (:_id doc)))
    (is (= "clojure.org" (:name doc)))
    (is (false? (:ipv6-enabled doc)))))