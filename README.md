# github-job-api-explorer

Testing the github API for popular queries.
Run 'lein run' for a quick overview

## Installation

Install leiningen https://github.com/technomancy/leiningen

## Usage

`lein run` gives an overview of what this can do
it's a basic application that allows you to query the github jobs API

run `lein test` to make sure that all the functionality works as expected...

# lein repl usage
If you want to start a repl session, try out:

`(ns user
  (:require [github-job-api-wrapper.wrapper :as wrapper]
            [clojure.pprint :as pp]))`

`(def results (wrapper/make-github-request))`

`(pp/print-table [:company :title :type :location :created_at] results)`

`(def results
  (wrapper/make-github-request :max-results 15 :query {:location "Tokyo" :full_time true}))`

`(pp/print-table [:company :title :type :location :created_at] results)`

Or try:

`(def results (wrapper/make-github-request :max-results 100))`

`(pp/pprint (wrapper/find-popular-langauges results))`

Where popular languages are defined as a vector in
`src/github_job_api_wrapper/wrapper.clj`

`(pp/pprint (wrapper/find-popular-acronyms results)))`

Where popular acronyms are defined as a vector in
`src/github_job_api_wrapper/wrapper.clj`

The find-popular-{languages,api} functions are not purely functional since the
list of popular X exists inside of the functions but it was the neatest way I
could think of doing it.

If running lein repl and want to test other popular keywords, define a vector of
keywords:

`(def some-words-im-interested-in ["chocolate" "table tennis" "coffee"])`

Then run:

`(def word-results
  (search-for-words-in-job-description results some-words-im-interested-in))`

Followed by:

`(pp/pprint word-results)`
