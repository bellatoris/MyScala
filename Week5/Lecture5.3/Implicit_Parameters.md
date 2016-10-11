# Lecture 5.3 - Implicit Parameters

## Making Sort more General
* Problem: How to parameterize `msort` so that it can also be used for lists with elements other than `Int`?

	```scala
	def msort[T](xs: List[T]): List[T] = ...
	```

* does not work, because the comparison < in `merge` is not defined for arbitrary types `T`.
* *Idea:* Paremeterize `merge` with the necessary comparison function:

## Parameterization of Sort
* THe most flexible design is to make the function `sort` polymorphic and to pass the comparison operation as an additional parameter:

	```scala
	def msort[T](xs: List[T])(lt: (T, T) => Boolean) = {
		...
			merge(msort(fst)(lt), msort(snd)(lt))
	}
	```

* Merge then needs to be adapted as follows:

	```scala
	def merge(xs: List[T], ys: List[T]) = (xs, ys) match {
		...
		case (x :: xs1, y :: ys1) =>
			if (lt(x, y)) ...
			else ...
	}
	```

* It's usually advantages if you have several parameter lists and one of them is a function value, to put the function value last. Because then you have a better chance that the type's already inferred by the time the compiler will type check the function value. And that means you don't have to wirte them explicitly. 

## Parametrization with Ordered
* There is already a class in the standard library that represents orderings.

	```scala
	scala.math.Ordering[T]
	```
	
	* provides ways to compare elements of type `T`. So instead of parameterizing with the `lt` operation directly, we could parameterize with `Ordering` instead:

	```scala
	def msrot[T](xs: List[T])(ord: Ordering[T]) = {
	
		def merge(xs: List[T], ys: List[T]) =
			... if (ord.lt(x, y)) ...
			
		... merge(msort(fst)(ord), msort(snd)(ord))
	}
	```
	
## Aside : Implicit Parameters
* *Problem:* Passing around *lt* or *ord* values is cumbersome.
* We can avoid this by making `ord` an implicit parameter.

	```scala
	def msort[T](xs: List[T])(implicit ord: Ordering) = {
		
		der merge(xs: List[T], ys: List[T]) = 
			... if (ord.lt(x, y)) ...
			
		... merge(msort(fst), msort(snd)) ...
	}
	```
* Then calls to `msort` can avoid the ordering parameters:

	```scala
	msort(nums)
	msort(fruits)
	```
	
* The compiler will figure out the right implicit to pass based on the demanded type

## Rules for Implicit Parameters
* Say, a function takes an implicit parameter of type `T`.
* The compiler will search an implicit definition that
	* is marked `implicit`
	* has a type compatible with `T`
	* is visible at the point of the function call, or is defined in a companion object associated with `T`.

* If there is a single (most specific) definition, it will be taken as actual argument for the implicit parameter.
* Otherwise it's an error.

## Exercise: Implicit Parameters
* Consider the following line of the definition of `msort`:

	```scala
	... merge(msort(fst), msort(snd)) ...
	```
	
* Which implicit argument is inserted?
	* Ordering.Int
	* Ordering.String
	* the `"ord"` parameter of `"msort"`
