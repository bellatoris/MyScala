# Lecture 5.2 - Pairs and Tuples

## Sorting Lists Faster
* As a non-tirival example, let's design a function to sort lists that is more efficient than insertion sort.
* A good algorithm for this is *merge sort*. The idea is as follows:
* If the list consists of zero or one elements, it is already sorted.
* Otherwise,
	* Separate the list into two sub-lists, each containing around half of the elements of the original list.
	* Sort the two sub-lists.
	* Merge the two sorted sub-lists into a single sorted list.

## First MergeSort Implementation
* Here is the implementation of that algorithm in Scala:

	```scala
	def msort(xs: List[Int]): List[Int] = {
		val n = xs.length / 2
		if (n == 0) xs
		else {
			def merge(xs: List[Int], ys: List[Int]): List[Int] = {
				if (xs.isEmpty) ys
				else if (ys.isEmpty) xs
				else if (xs.head < ys.head) xs.head :: merge(xs.tail, ys)
				else ys.head :: merge(xs, ys.tail)
			}
			val (fst, snd) = xs splitAt n 
			merge(msort(fst), msort(snd))
		}
	}
	``` 
	
## Definition of Merge
* Here is a definition of the `merge` function:

	```scala
	def merge(xs: List[Int], ys: List[Int]): List[Int] = {
		xs match {
			case Nil => ys
			case x :: xs1 => ys match {
				case Nil => xs
				case y :: ys1 => 
					if (x < y) x :: merge(xs1, ys) 
					else y :: merge(xs, ys1)
			}
		}
	}
	```
	
## The SplitAt Function
* The `splitAt` function on lists returns two sublists
	* the elements up the given index
	* the elements from that index

* The lists are returned in a `pair` 

## Detour: Pair and Tuples
* THe pair consisting of `x` and `y` is written `(x, y)` in Scala
* **Example**

	```scala
	val pair = ("answer", 42)
	> pair: (String, Int) = (answer, 42)
	```
	
* The types of `pair` above is `(String, Int)`.
* Pairs can also be used as patterns:

	```scala
	val (label, value) = pair
	> label: String = answer
	| value: Int = 42
	```

* This works analogously for tuples with more than two elements.
 