# Dandelion

[![Clojars Project](https://img.shields.io/clojars/v/pbalduino/dandelion.svg)](https://clojars.org/pbalduino/dandelion)

[![cljdoc badge](https://cljdoc.org/badge/pbalduino/dandelion)](https://cljdoc.org/d/pbalduino/dandelion/CURRENT)

## What?

`Dandelion` is a small and lightweight library that allows you to convert Clojure data to Ion data and vice-versa without the burden to dive third-party source code or rely on outdated and half-baked documentation.

## Why?

From [Ion Docs](http://amzn.github.io/ion-docs/guides/why.html):

> Ion provides dual-format interoperability, which enables users to take advantage of the ease of use of the text format while capitalizing on the efficiency of the binary format. The text form is easy to prototype, test, and debug, while the binary format saves space and parsing effort.
>
> Ion’s rich type system extends JSON’s, adding support for types that make Ion suitable for a wider variety of uses, including precision-sensitive applications and portability across languages and runtimes.
>
> Ion is a self-describing format, giving its readers and writers the flexibility to exchange Ion data without needing to agree on a schema in advance. Ion’s “open-content” supports discovery, deep component chaining, and schema evolution.
>
> Because most data is read more often than it is written, Ion defines a read-optimised binary format.

And because you're now writing something in Clojure (yeah!) and need to interoperate with this awkward format (boo!).

## How?

### Lein/Boot
```
[pbalduino/dandelion "0.1.1"]
```

### deps.edn
```
pbalduino/dandelion {:mvn/version "0.1.1"}
```

### Samples

```clojure
(require [dandelion.core :refer [clj->ion ion->clj])

(def sample-data {"text" "some text"
                  "number" 1234
                  "array" [1, 2, 3, 4]
                  "obj" {"more-array" [5, 6, 7]
                         "more-number" 789.3}
                  "bool" true
                  "null" nil})

;; To transform Clojure data in Ion value
(-> sample-data ; some Clojure data
    clj->ion    ; where the magic happens
    str         ; convert to a readable string
    println)    ; show me the money

; {text:"some text",number:1234,array:[1,2,3,4], ↵
;  obj:{'more-array':[5,6,7],'more-number':789.3}, ↵
;  bool:true,'null':null}

;; To transform Ion value to Clojure data
(def ion-value (clj->ion sample-data))

(-> ion-value              ; Ion value
    ion->clj               ; simple, uh?
    clojure.pprint/pprint) ; welcome back

; {"text" "some text",
;  "number" 1234,
;  "array" [1 2 3 4],
;  "obj" {"more-array" [5 6 7], "more-number" 789.3},
;  "bool" true,
;  "null" nil}
```

That's all.

## Who?
[Plínio Balduino](https://github.com/pbalduino), software developer and problem solver.

### Contributors (Thank you!)
* Shayan Fouladi
