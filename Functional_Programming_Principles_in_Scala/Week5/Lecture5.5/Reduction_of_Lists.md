# Lecture 5.5 - Reduction of Lists

## Reduction of Lists
* Another common operation on lists is to combine the elements of a list using a given operator.
* For example:

	```scala
	sum(List(x1, ..., xn))		= 0 + x1 + ... + xn
	product(List(x1, ..., xn))	= 1 * x1 * ... * xn
	```

* We can implement this with the usual recursive schema:

	```scala
	def sum(xs: List[Int]): Int = xs match {
		case Nil => 0
		case y :: ys => y + sum(ys)
	}
	``` 
	
## ReduceLeft
* This pattern can be abstracted out using the genric method `reduceLeft`: 
	* `reduceLeft` inserts a given binary operator between adjacent elements of a list:

	```scala
	List(x1, ..., xn) reduceLeft op = (...(x1 op x2) op ...) op xn
	```
	
* Using `reduceLeft`, we can simplify:

	```scala
	def sum(xs: List[Int])		= (0 :: xs) reduceLeft ((x, y) => x + y)
	def product(xs: List[Int])	= (1 :: xs) reduceLeft ((x, y) => x * y)
	```
	
## A Shorter Way to Write Functions
* Instead of `((x, y) => x * y)`, one can also write shorter:

	```scala
	(_ * _)
	```

* Every `_` represents a new parameter, going from left to right.
* The parameters are defined at the next outer pair of parentheses (or the whole expression if there are no enclosing parentheses).
* So, `sum` and `product` can also be expressed like this:

	```scala
	def sum(xs: List[Int])		= (0 :: xs) reduceLeft (_ + _)
	def product(xs: List[Int])	= (1 :: xs) reduceLeft (_ * _)
	```
	
## FoldLeft
* The function `reduceLeft` is defined in terms of a more general function, `foldLeft`.
* `foldLeft` is like `reduceLeft` but takes an *accumulator*, `z`, as an additional parameter, which is returned when `foldLeft` is called on an empty list.

	```scala
	(List(x1, ..., xn) foldLeft z)(op) = (...(z op x1) op ...) op xn
	```
	
* So, `sum` and `product` can also be defined as follows:

	```scala
	def sum(xs: List[Int])		= (xs foldLeft 0)(_ + _)
	def product(xs: List[Int])	= (xs foldLeft 1)(_ * _)
	```
	
## Implementations of ReduceLeft and FoldLeft
* `foldLeft` and `reduceLeft` can be implemented in class `List` as follows.

	```scala
	abstract class List[T] { ...
		def reduceLeft(op: (T, T) => T): T = this match {
			case Nil => throw new Error("Nil.reduceLeft")
			case x :: xs => (xs foldLeft x)(op)
		}
		def foldLeft[U](z: U)(op: (U, T) => U): U = this match {
			case Nil => z
			case x :: xs => (xs foldLeft op(z, x))(op)
		}
	}
	```
	
## Right and ReduceRight
* Applications of `foldLeft` and `reudceLeft` unfold on trees that lean to the left.
* They have two dual functions, `foldRight` and `reduceRight`, which produce trees which lean to the right, i.e.,

	```scala
	List(x1, ..., x{n-1}, xn) reduceRight op	= x1 op (... (x{n-1} op xn) ...)
	(List(x1, ..., xn) foldRight acc)(op)		= x1 op (... (xn op acc) ...)
	```

## Implementations of FoldRight and ReduceRight
* They are defined as follows

	```scala
	def reduceRight(op: (T, T) => T): T = this match {
		case Nil => throw new Error("Nil.reduceRight")
		case x :: Nil => x
		case x :: xs => op(x, xs.reduceRight(op))
	}
	def foldRight[U](z: U)(op: (U, T) => U): U = this match {
		case Nil => z
		case x :: xs => op(x, (xs foldRight z)(op))
	}
	```

## Difference between FoldLeft and FoldRight
* For operators that are associative and commutative, `foldLeft` and `foldRight` are equivalent (even though there may be a difference in efficiency).
* But sometimes, only one of the two operators is appropriate.

## Exercise
* Here is another function of concat:

	```scala
	def concat[T](xs: List[T], ys: List[T]): List[T] =
		(xs foldRight ys) (_ :: _)
	```

* Here, it isn't possible to replace `foldRight` by `foldLeft`. Why?
	* The types would not work out
	* The resulting function would not terminate
	* The result would be reserved

* Answer: The types would not work out. `ys :: x` can't be.	

* Complete the following definitions of the basic functions map and length on lists, such that their implementation uses `foldRight`:

	```scala
	def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  		(xs foldRight List[U]())(f(_) :: _)

	def lengthFun[T](xs: List[T]): Int =
   		(xs foldRight 0)((x, y) => 1 + y)
  	```