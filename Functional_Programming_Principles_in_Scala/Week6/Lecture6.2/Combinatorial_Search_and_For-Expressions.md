# Lecture 6.2 - Combinatorail Search and For-Expressions
## Handling NEsted Sequences 
* We can extend the usage of higher oreder functions on sequences to many calculations which are usually expressed using nested loops.
* **Example:** Given a positive integer `n`, find all pairs of positive integers `i` and `j`, with `1 <= j < i < n` such that `i + j` is prime.
* For example, if `n = 7`, the sought pairs are


	| i     | 2  | 3  | 4 | 4 | 5 | 6 | 6  |
	|:----- |:-- |:-- |:--|:--|:--|:--|:-- |
	| j     | 1  | 2  | 1 | 3 | 2 | 1 | 5  |
	| i + j | 3  | 5  | 5 | 7 | 7 | 7 | 11 |


## Algorithm
* A natural way to do this is to:
	* Generate the sequence of all pairs of integers `(i, j)` such that `1 <= j < i < n`
	* Filter the pairs for which `i + j` is prime.
	
* One natural way to generte the sequence of pairs is to:
	* Generate all the integers `i` between `1` and `n` (excluded).
	* For each integer `i`, generate the list of pairs `(i, 1), ..., (i, i - 1)`.

* This can be achieved by combining `until` and `map`:

	```scala
	(1 until n) map (i =>
		(1 until i) map (j => (i, j)))
	```
	  
* 결과는  vectors of vectors로 나온다. Range로는 pairs 를 표현 할 수 없다. IndexedSeq로 올라간 후 vector로 implement한다.
* 우리는 모든 pair의 list를 원하므로 vectors of vectors를 concatenate해야 한다.

## Generate Pairs
* Thre previous step gave a sequence of sequences, let;s call it `xss`.
* We can combine all the sub-sequences using `foldRight` with `++`:

	```scala
	(xss foldRight Seq[Int]()))(_ ++ _)
	```
	
* Or, equivalently, we use the built-in mehtod `flatten`

	```scala
	xss.flatten
	```
	
* This gives:

	```scala
	((1 until n) map (i =>
		(1 until i) map (j => (1, j)))).flatten
	```
	
* Here;s useful law:

	```scala
	xs flatMap f = (xs map f).flatten
	```
	
* Hence, the above exprseeion can be simplified to

	```scala
	(1 until n) flatMap (i =>
		(1 until i) map (j => (i, j)))
	```
	
## For-Expressions
* Higher-order functions such as `map`, `flatMap`, or `filter` provide poewrful constructs for manipulating lists.
* But sometimes the level of abstraction required by these function make the program difficult to understand.
* In this case, Scala's for expression notation can help.

## For-Expression Example
* Let `persons` be a lsit of elements fof class `Person`, with fields `name` and `age`.

	```scala
	case class Person(name: String, age: Int)
	```
	
* To obtain the names of persons over 20 years old, you can write:

	```scala
	for (p <- persons if p.age > 20) yield p.name
	```
which is equivalent to:

	```scala
	persons filter (p => p.age > 20) map (p => p.name)
	```

* The for-expressions is similar to loops in imperative languages, except that it builds a list of the results of all iterations.

## Syntax of For
* A for-expression is of the form

	```scala
	for ( s ) yield e
	```

* where `s` is a sequence of *generators* and *filters*, and `e` is an expression whose value is returned by an iteration.
	* A *generator* is of the form `p <- e`, where `p` is a pattern and `e` an expression whsoe value is a collection.
	* A *filter* is of the form if `f` where `f` is a boolean expression.
	* The sequence must start with a generator.
	* If there are several generators in the sequence, the last generators vary faster than the first.

* Instead of `( s )`, braces `{ s }` can also be used, and then the sequence of generators and fiters can be written on multiple lines without requring semicolons.

## Use of For
* Here are two examples which were preciously solved with higher-order functions:
* Given a prositive integer `n`, find all the pairs of positive integers `(i, j)` such that `1 <= j < i < n`, and `i + j` is prime.

	```scala
	for {
		i <- 1 until n
		j <- 1 until i
		if isPrime(i + j)
	} yield (i, j)
	```
	
## Exercise
* Write a version of `scalarProduct` (see last session) that makes use of a `for`:

	```scala
	def scalarProduct(xs: List[Double], ys: List[Double]): Double = 
		(for ((x,y) <- xs zip ys) yield x * y).sum
	```