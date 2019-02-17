(ns github-job-api-wrapper.wrapper
  (:require [github-job-api-wrapper.wrapper :refer :all]
            [clj-http.client :as client]
            [clojure.string :as cs]))

(defn make-request
  ; Given a string URL make that request!
  ; Set insecure to true in case this is run on an enterprise network with 
  ; self-signed TLS certificates injected in the middle...
  [url & {:keys [query-params json] :or {query-params {} json true}}]
  (def http-args (if json {:insecure? true :as :json :query-params query-params}
                     {:insecure? true :query-params query-params}))
  (client/get url http-args))

(defn github-request
  [query page-no]
  (def github-url "https://jobs.github.com/positions.json")
  (get
   (make-request github-url :query-params (assoc query :page page-no)) :body))

(defn make-github-request
  ;; Given optional query / max-results parameters paginate through results to
  ;; return the requested number of results in an array Array<HashMap> structure
  [& {:keys [query max-results] :or {query {} max-results 10}}]

  ;; Given that there are 50 results to a page, tell us how many pages of results
  ;; to get given the number of requested results
  (def pages (+ 1 (quot max-results 51)))
  (def github-request-partial (partial github-request query))
  ;; Full disclosure -
  ;; I spent some time here thinking imperatively:
  ;; i.e.
  ;; (loop [page 1
  ;;        results ()]
  ;;   (if (>= pages page)
  ;;     (recur (inc page) (concat results (github-request-partial page)))
  ;;   (take max-results results ))))
  ;; which does work but it took me a while to 'get it' (using the map / flatten
  ;; below)
  (->> (map github-request-partial (range 1 (+ pages 1)))
       (flatten)
       (take max-results)))

(defn search-for-words-in-job-description
  ; Given some results, search through the descriptions with some regex to find
  ; and create a hashmap structured Hashmap<String, int> 
  [job-results word-list]
  ;; Count the frequencies of applying a regex to a lowercase job description
  ;; which includes the above words
  (->> (for [job job-results]
         (map #(re-find (re-pattern (str "\\b(?i)" % "\\b"))
                        (cs/lower-case (get job :description))) word-list))
       (flatten) (remove nil?) (frequencies)))

(defn find-popular-langauges
  ; Given some results, search through the descriptions with some regex to find
  ; and create a hashmap structured Hashmap<String, int> 
  [job-results]
  (def popular-languages
    ["bash"
     "c#"
     "clojure"
     "f#"
     "go"
     "groovy"
     "haskell"
     "java"
     "javascript"
     "ocaml"
     "perl"
     "php"
     "python"
     "ruby"
     "rust"
     "scala"
     "sql"])
  (search-for-words-in-job-description job-results popular-languages))

(defn find-popular-acronyms
  ; Given some results, search through the descriptions with some regex to find
  ; and create a hashmap structured Hashmap<String, int> 
  [job-results]
  (def popular-languages
    ["api"
     "bgp"
     "cpu"
     "dhcp"
     "dns"
     "eof"
     "ibm"
     "ocf"
     "osi"
     "wan"])
  (search-for-words-in-job-description job-results popular-languages))
