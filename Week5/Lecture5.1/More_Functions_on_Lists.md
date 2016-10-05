# Lecture 5.1 - More Functions on Lists

# List Methods
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

# Implementation of last
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
	