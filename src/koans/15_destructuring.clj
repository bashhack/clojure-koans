(ns koans.15-destructuring
  (:require [koan-engine.core :refer :all]
            [clojure.string :as string]))

(def test-address
  {:street-address "123 Test Lane"
   :city "Testerville"
   :state "TX"})

(meditations
 "Destructuring is an arbiter: it breaks up arguments"
 (= ":bar:foo" ((fn [[a b]] (str b a))
                [:foo :bar]))

 "Whether in function definitions"
 (= (str "An Oxford comma list of apples, "
         "oranges, "
         "and pears.")
    ((fn [[a b c]]
       (str "An Oxford comma list of " a ", " b ", " "and " c "."))
     ["apples" "oranges" "pears"]))

 "Or in let expressions"
 (= "Rich Hickey aka The Clojurer aka Go Time aka Lambda Guru"
    (let [[first-name last-name & aliases]
          (list "Rich" "Hickey" "The Clojurer" "Go Time" "Lambda Guru")]
      (apply str
             (interpose " "
                        (cons first-name
                              (interpose "aka" (cons last-name aliases)))))))

 ;; An alternate solution here could also have been...
 ;; ((let [[first-name last-name & aliases]
 ;;        (list "Rich" "Hickey" "The Clojurer" "Go Time" "Lambda Guru")]
 ;;    (str
 ;;     first-name " "
 ;;     (let [[x y z] aliases]
 ;;       (apply str (interpose " aka " [last-name x y z]))))))

 ;; Or...a little less elegantly, perhaps
 ;; ((let [[first-name last-name & aliases]
 ;;        (list "Rich" "Hickey" "The Clojurer" "Go Time" "Lambda Guru")]
 ;;    (str first-name " "
 ;;         last-name " aka "
 ;;         (nth aliases 0) " aka "
 ;;         (nth aliases 1) " aka "
 ;;         (nth aliases 2))))

 "You can regain the full argument if you like arguing"
 (= {:original-parts ["Stephen" "Hawking"] :named-parts {:first "Stephen" :last "Hawking"}}
    (let [[first-name last-name :as full-name] ["Stephen" "Hawking"]]
      {:original-parts full-name
       :named-parts {:first first-name :last last-name}}))

 "Break up maps by key"
 (= "123 Test Lane, Testerville, TX"
    (let [{street-address :street-address, city :city, state :state} test-address]
      (apply str (interpose ", " [street-address city state]))))

 "Or more succinctly"
 (= "123 Test Lane, Testerville, TX"
    (let [{:keys [street-address city state]} test-address]
      (apply str (interpose ", " [street-address city state]))))

 "All together now!"
 (= "Test Testerson, 123 Test Lane, Testerville, TX"
    ((fn [[first-name last-name] {:keys [street-address city state]}]
       (apply str (interpose ", " [(string/join " " [first-name last-name])
                                   street-address city state])))
     ["Test" "Testerson"] test-address)))


