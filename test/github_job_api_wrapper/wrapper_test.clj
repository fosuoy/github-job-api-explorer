(ns github-job-api-wrapper.wrapper-test
  (:require [clojure.test :refer :all]
            [github-job-api-wrapper.wrapper :as api-wrapper]))

(deftest internetAccess
  (testing "Is a network connection present / can you make requests?"
    (is (= (get (api-wrapper/make-request "http://www.google.com/" :json false) :status) 200)
        "Running this requires internet access, you may not currently be connected, alternative google might be down.")))

(deftest regexing
  (testing "Do my regexes work..."
    ;; regexes should only count one instance of python from the job-results
    ;; variable since pythonjavabash is just spamming keywords...
    (def job-results
      [{:description "pythonjavabash"}
       {:description "python"}
       {:description "Python"}])
    (is (= (api-wrapper/find-popular-langauges job-results) {"python" 2}))))
