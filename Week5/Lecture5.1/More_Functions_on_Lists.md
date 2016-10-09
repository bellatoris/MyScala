# Lecture 5.1 - More Functions on Lists

## List Methods
* *Sublists and element access*:

	```scala
	xs.length 			// The number of elements of xs
	xs.last				// The list's last element, exception if xs is empty.
	xs.init 			// A list consisting of all elements of xs except the
						// last one, exception if xs is empty.
	xs take n			// A list consisting of first n elements of xs, or xs
						// itself if it is shorter than n.
	xs drop n			// The rest of the collection after taking n elements
	xs(n) 				// (or, written out, xs apply n). The element of xs at 
						// index n.
	```

* *Creating new lists*:

	```scala
	xs ++ ys			// The list consisting of all elements of xs followed
						// by all elements of ys.
	xs.reverse			// The list containing the elements of xs in reversed
						// order.
	xs.updated(n, x)	// The list containing the same elements as xs, except 
						// at index n where it contains x.	```
						
* *Finding elements*:

	```scala
	xs indexOf x		// THe index of the first element in xs equal to x, or
						// -1 if x does not apear in xs.
	xs contains x		// same as xs indexOf x >= 0
	```

## Implementation of last
* The complexity of `head` is (small) constant time.
* What is the complexity of `last`?	
* To find out, let's write a possible implementatino of `last` as a stand-alone function.

	```scala
	def last[T](xs: List[T]): T = xs match {
		case List() => throw new Error("last of empty list")
		case List(x) => x
		case y :: ys => last(ys)
	}
	```
* So, `last` takes steps proportional to the length of the list `xs`.

## Exercise
* Implement `init` as an external function, analogous to `last`.

	```scala
	def init[T](xs: List[T]): List[T] = xs match {
		case List() => throw new Error("init of empty list")
		case List(x) => List()
		case y :: ys => y :: init(ys)
	}
	```

* Because `ys`'s size is more than one, it cannot fail.

##	Implementation of Concatenation
* How can concatenation be implemented? `xs ::: ys = ys. ::: (xs)`
* Let's try by writing a stand-alone function:

	```scala
	def concat[T](xs: List[T], ys: List[T]) = xs match {
		case List() => ys
		case z :: zs => z :: concat(zs, ys)
	}
	```
	
	* The first element of the result list is from the `xs`. So it makes sense to pair an match in `xs`.

* What is the complexity of `concat`?
	* Length of `xs`

## Implementation of `reverse`
* How can `revesre` be implemented?
* Let's try by writing a stand-alone function:

	```scala
	def reverse[T](xs: List[T]): List[T] = xs match {
		case List() => List()
		case y :: ys => reverse(ys) ++ List(y)
	}
	```
	
* What is the complexity of `reverse`? 
	* Length of `xs` ^ 2 (concatenation has linear time complexity)
* *Can we do better?* (to be solved later).

## Exercise
* Remove the n'th element of a list `xs`. If n is out of bounds. return `xs` itself.

	```scala
   def removeAt(n: Int, xs: List[Int]) = (xs take n) ::: (xs drop n + 1) 
	```

* Usage example:

	```scala
	removeAt(1, List('a', 'b', 'c', 'd')) // List(a, c, d)
	```
	
* Flatten a list structure:

	```scala
	def flatten(xs: List[Any]): List[Any] = xs match {
		case List() => List()
    	case y :: ys => y match {
      		case z :: zs => flatten(z :: zs) ::: flatten(ys)
      		case z => z :: flatten(ys)
    }
	
	flatten(List(List(1, 1), 2, List(3, List(5, 8))))
	
	> res0: List[Any] = List(1, 1, 2, 3, 5, 8)
	```