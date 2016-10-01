# Lecture 4.3 - Subtyping and Generics

## Polymorphism
* Two principal forms of polymorphism:
	* subtyping
	* generics

* In this session we will look at their interactions.
* Two main areas:
	* bounds
	* variance

## Type Bounds
* Consider the mehtod `assertAllPos` which
	* takes an `InSet`
	* returns the `InSet` itself if all its elements are positive
	* throws an exception otherwise

* What would be the best type you cna give to `assertAllPos`? Maybe: 	  
```scala
	def assertAllPos(s: IntSet): IntSet
```

* In most situations this is fine, but can one be more precise?