
(defn my-do-all
  "Takes a list, a function that takes two arguments and an optional starting value.
  If starting value is supplied applies the function to the starting value and the 
  first element of the list, then applies the function to that result together with
  the next element in the list and so on, until no more elements remain, then returns 
  the result. If no starting value is supplied it starts from the first and second 
  elements of the list"
  ([lst func] 
   (my-do-all (rest lst) func (first lst)))
  ([lst func result-so-far]
   (if (empty? lst)
     result-so-far
     (recur (rest lst) func (func result-so-far (first lst))))))



(defn my-max
  "Takes a list of numbers and returns the largest number of that list"
  [lst]
  (my-do-all 
    lst 
    (fn [max num] 
      (if (> num max) 
        num 
        max))))


(defn my-min
  "Takes a list of numbers and returns de smallest number of that list"
  [lst]
  (my-do-all 
    lst
    (fn [min num] 
      (if (< num min) 
        num 
        min))))

(defn my-reverse
  "Takes a list and returns a new list with the elements in reverse order. If supplied 
  argument is not a list, returns nil"
  ([lst]
   (if (list? lst)
    (my-reverse lst ())
    nil))
  ([lst result-so-far]
   (if (empty? lst)
     result-so-far
     (recur (rest lst) (conj result-so-far (first lst))))))


(defn my-map
  "Takes a list and a function that takes one argument. Returns a new list with the result
  of applying the function to every element in the input list"
  ([lst func]
   (my-reverse 
     (my-do-all 
       lst 
       (fn [done elem] 
         (conj done (func elem))) 
       ()))))
       

;;A downside to the solution for my-checksum-2 might be that it is a little bit inefficient
;;We step through the list once with my-map to create a new list, then step through that list
;;to calculate the final checksum. It might have been more efficient to step through the original 
;;list and calculating up towards the final checksum at the same time as we were calculating the 
;;individual checksums instead of making a new lists and stepping over that.
;;I like the simplicity and readability of the solution right now though. 

(defn my-checksum2
  "Takes a list containing lists of numbers and two functions. The first function is used to 
  calculate a checksum for every individual list in the input list. The second function is used
  to calculate the final checksum from the checksums of the individual lists."
  [lst f-individual f-total]
  (my-do-all (my-map lst f-individual) f-total))

  
(defn my-checksum1
  "Takes a list containing lists of numbers. Calculates the difference between the largest and the 
  smallest number of every individual list, then return the sum of all the differences for the individual lists"
  ([lst]
   (my-checksum2 
     lst 
     (fn [indv-lst] 
       (- (my-max indv-lst) (my-min indv-lst)))
     +)))


