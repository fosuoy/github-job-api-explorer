(ns github-job-api-explorer.core
  (:require [clojure.test :refer :all]
            [github-job-api-explorer.core :refer :all]
            [clojure.tools.cli :refer :all]
            [github-job-api-wrapper.wrapper :as wrapper]
            [clojure.reflect :as reflect]
            [clojure.pprint :as pp])
  (:gen-class))

(defn -main
  "Insert here some basic print statements to output the goals then the commands
  to run in 'lein repl'."
  [& args]
  (println "Printing out the commands that will be run...")
  (println "If you want to follow along using lein repl, make sure to run:")
  (println "(ns user (:require [github-job-api-wrapper.wrapper :as wrapper] [clojure.pprint :as pp]))")
  (println "All commands will usually save the resulting map to a variable 'results' then print it out using:")
  (println "To display results: (pp/print-table [:company :title :type :location :created_at] results))")
  (println
   "Running: (def results (wrapper/make-github-request))")
  (println
    "Followed by: (pp/print-table [:company :title :type :location :created_at] results)")
  (def results (wrapper/make-github-request))
  (pp/print-table [:company :title :type :location :created_at] results)

  (println
   "Running: (def results (wrapper/make-github-request :max-results 15 :query {:location \"Tokyo\" :full_time true}))")
  (println
    "Followed by: (pp/print-table [:company :title :type :location :created_at] results)")
  (def results (wrapper/make-github-request :max-results 15 :query {:location "Tokyo" :full_time true}))
  (pp/print-table [:company :title :type :location :created_at] results)

  (println
   "Running: (def results (wrapper/make-github-request :max-results 100))")
  (def results (wrapper/make-github-request :max-results 100))

  (println
   "Running: (pp/pprint (wrapper/find-popular-langauges results))")
  (pp/pprint (wrapper/find-popular-langauges results))

  (println
   "Running: (pp/pprint (wrapper/find-popular-acronyms results))")
  (pp/pprint (wrapper/find-popular-acronyms results)))
