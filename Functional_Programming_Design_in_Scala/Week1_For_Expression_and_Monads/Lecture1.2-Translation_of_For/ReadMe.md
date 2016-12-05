# Lecture 1.2 - Translation of For

## For-Expressions and Higher-Order Functions
* The syntax of `for` is closely related to the higher-order functions `map`, `flatMap` and `filter`.
* First of all, these functions can all be defined in terms of `for`:

	```scala
	def mapFun[T, U](xs: List[T], f: T => U): List[U] = 
		for (x <- xs) yield f(x)
		
	def flatMap[T, U](xs: List[T], f: T => Iterable[U]): List[U] = 
		for (x <- xs; y <- f(x)) yield y
		
	def filter[T](xs: List[T], p: T => Boolean): List[T] =
		for (x <- xs if p(x)) yield x
	```

## Translation of For
* In reality, the Scala complier expresses for-expressions in terms of `map`, `flatMap` and lazy varient of `filter`.
* Here is the translation scheme used by the compiler (we limit ourselves here to simple variables in generators).

1. A simple for-expression

	```scala
	for (x <- e1) yield e2
	```
	
	is translated to
	
	```scala
	e1.map(x => e2)
	```