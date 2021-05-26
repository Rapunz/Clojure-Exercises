


(defmacro safe
  ([form]
   `(safe [] ~form))
  ([binding form]
   `(try 
     (let ~binding
      (try
        ~form
       (catch Exception e# e#)
       (finally (if (instance? java.io.Closeable ~(first binding)) (. ~(first binding) close)))))
     (catch Exception e# e#))))     
     



 ;A safe function for only an expression could look like 
;(defn safe [form] (try form (catch Exception e# e#)))
;If we call it like (def v (safe (/ 1 0)))
;we get an exception right away, the exception is never returned from
;safe or stored in v,  v never gets bound at all. For
;functions, Clojure uses applicative-order evaluation (eager evaluation) and arguments
;are evaluated before they are sent. (/ 1 0) will
;get evaluated and throw an exception before safe is even called. 


;For a call like 
;(def v (safe [s (FileReader. (File. "file.txt"))] (.read s)))
;this means that if the read-operation goes wrong, safe will never get
;called, our finally-clause will never run and the fileReader doesn’t close. 
;Worse is that when the arguments are evaluated, the symbol s is also evaluated, but
;nothing is yet bound to the symbol s, we get an error and the call is never made.

;We can get this working with a function though, if we
;require the caller to quote the binding form and the expression
;so that they don’t get evaluated right away. We can then use some
;workarounds with eval and other stuff to evaluate them only when we need them in our function.

;With macros the arguments are not eagerly evaluated they
;produce code at compile time with the sent in arguments inserted.
;That code then replaces the call to the macro and therefore we don’t
;have the same problems.

;Macros are powerful and should be handled with care.
;With macro’s you also miss out on some of the good things about functions. 
;In this case we probably should use a macro though, to do the same thing
;with a function requires some workarounds and requires the user of
;the function to quote all the arguments. 






