# Lecture 1.1 - Programming Paradigms

## Programming Paradigms

Paradigm: In science, a ***paradigm*** describes distinct concepts or thought patterns some scientific discipline. Main programming paradigms:

* imperatvie programming
* functional programming
* logic programming

Orthogonal to it:

* object-oriented programming

## Review: Imperative programming
Imperative programming is about

* modifying mutable variables,
* using assingments
* and control structures such as if-then-else, loops, break, continue, return.

The most common informal way to understand imperative programs is as instruction sequences for a Von Neumann computer.

## Imperative Programs ans Computers
There's a strong correspondence between

* Mutable variables ~= memory cells
* Variable dereferences ~= load instructions
* Variable assignments ~= store insturctions
* Control structures ~= jumps

***Problem***: Scaling up. How can we avoid conceptualizing programs word by word?

## Scaling Up
 In the end, pure imperative programming is limited by the "Von Neumann" bottleneck:
 
 > One tends to conceptualize data structures word-by-word.

We need other techniques for defining high-level abstractions such as collections, polynomials, geometric shapes, strings, documents.  
Ideally: Develop *theories* of collections, shapes, strings, ...

## What is a Theory?
A theory consists of 

* one or more data types 
* operations on these types
* laws that describe the relationships between values and operations

Noramlly, a theory does not describe **mutations**!

## Theories without Mutation
For instance the theory of polynomials defines the sum of two polynomials by laws such as:

`(a*x + b) + (c*x + d) = (a+c)*x + (b+d)`
But it does not define an operator to change a coefficient while keeping the polynomial the same!

***Other example:***  
The theory of strings defines a concatenation operator `++` which is associative:

`(a ++ b) ++ c = a ++ (b ++ c)`

But it does not define an operator to change a sequence element while keeping the sequence the same!

## Consequences for Programming
Let's

* concentrate on defining theories for operators,
* minimize state changes.
* treat operators as functions, often composed of simpler functions.

## Functional Programming
* In a *restricted* sense, functional programming (FP) means programming without mutable variables, assignments, loops, and other imperative control structures.
* In a *wider* sence, functional programming means focusing on the functions.
* In particular functions can be values that are produed, consumes, and composed.
* All this becomes easier in a functional language.
	* they can defined anywhere, including inside other functions
	* like any other value, they can be passed as parameters to functions and returned as results
	* as for other values, there exists a set operators to compose functions

## Some functional programming languages
In the restricted sense:

* Pure Lisp, XSLT, XPath, XQuery, FP
* Haskell (without I/O Monad or UnsafePerfomIO)

In the wider sense:

* Lisp, Scheme, Racket, Clojure
* SML, Ocaml, F#
* Haskell (full language)
* Scala
* Smalltalk, Ruby (!)

## Why Functional Programming?
Functional Programming is becoming increasingly popular because it offers an attractive method for exploiting parallelism for multicore and cloud computing.