# Lecture 1.3 - Functional Random Generators

## Other Uses of For-Expressions
* Operations of sets, or databases, or options.
* *Questions:* Are for-expressions tied to collections?
* *Answer:* No! All that is required is some interpretation of `map`, `flatMap` and `withFilter`.
* There are many domains outside collections that afford such an interpretation.
* Example: random value generators.

## Random Values
* You know about random numbers:

	```scala
	import java.util.Random
	val rand = new Random
	rand.nextInt()
	```
	
* Question: What is a systematic way to get random values for other domains, such as
	* booleans, strings, pairs and tuples, lists, sets, trees ?

## Generators
* Let's define a trait `Generator[T]` that generates random values of type `T`:

	```scala
	trait Generator[+T] {
		def generate: T
	}
	```
	
* Some instances:

	```scala
	val integers = new Generator[Int] {
		val rand = new java.util.Random
		def generate = rand.nextInt()
	}
	
	val booleans = new Generator[Boolean] {
		def generate = integers.generate > 0
	}
	
	val pairs = new Generator[(Int, Int)] {
		def generate = (integers.generate, integers.generate)
	}
	```
	
	How can we delete above boilerplate code? 
	
## Streamlining It
* Can we avoid the `new Generator` ... **boilerplate** (행사코드)?
* Ideally, we would like to write:

	```scala
	val booleans  = for (x <- integers) yield x > 0
	
	def pairs[T, U](t: Generator[T], u: Generator[U]) = for {
		x <- t
		y <- u
	} yield (x, y)
	```
	
	What does this expand to?
	
	```scala
	val booleans = integers map (x => x > 0)
	
	def pairs[T, U](t: Generator[T], u: Generator[U]) = 
		t flatMap (x => u map (y => (x, y)))
	```
	
	Need `map` and `flatMap` for that!
	
## Generator with `map` and `flatMap`
* Here's a more convenient version of `Generator`:

	```scala
	trait Generator[+T] {
		self => 		// an alias for "this".
		
		def generate: T
		
		def map[S](f: T => S): Generator[S] = new Generator[S] {
			def generate = f(self.generate) // same with f(Generator.this.generate)
		}
		
		def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
			def generate = f(self.generate).generate
		}
	}
	```
	
## The `booleans` Generator
* What does this definition resolve to?

	```scala
	val booleans  = for (x <- integers) yield x > 0
	
	val booleans = integers map { x => x > 0 }

	val booleans = new Generator[Boolean] {
		def generate = (x: Int => x > 0)(integers.generate)
	}  	
	
	val booleans = new Generator[Boolean] {
		def generate = integers.generate > 0
	}
	```
	
## The `pairs` Generator
```scala
def pairs[T, U](t: Generator[T], u: Generator[U]) = t flatMap { 
	x => u map { y => (x,y) } }
	
def pairs[T, U](t: Generator[T], u: Generator[U]) = t flatMap {
	x => new Generator[(T, U)] { def generate = (x, u.generate) } }
	
def pairs[T, U](t: Generator[T], u: Generator[U]) = new Generator[(T, U)] {
	def generate = (new Generator[(T, U)] {
		def generate = (t.generate, u.generate)
	}).generate }
	
def pairs[T, U](t: Generator[T], u: Generator[U]) = new Generator[(T, U)] {
	def generate = (t.generate, u.generate)
} 
```

## Generator Examples
```scala
def single[T](x: T): Generator[T] = new Generator[T] { 
	def generate = x
}

def choose(lo: Int, hi: Int) : Generator[Int] = 
	for (x <- integers) yield lo + x % (hi - lo)

// T* means that you can give it as many choices as you want
def oneOf[T](xs: T*): Generator[T] =
	for (idx <- choose(0, xs.length)) yield xs(idx)
```

## A `List` Generator
We can write random value generators for more structured types. A list is either an empty list or a non-empty list.

```scala
def lists: Generator[List[Int]] = for { 
	isEmpty <- booleans
	list <- if (isEmpty) emptyLists else nonEmptyLists
} yield list

def emptyLists = single(Nil)

// recursion
def nonEmptyLists = for { 
	head <- integers
	tail <- lists
} yield head :: tail
```

## A `Tree` Generator
Can you implement a generator that creates random `Tree` objects?

```scala
trait Tree

case class Inner(left: Tree, right: Tree) extends Tree

case class Leaf(x: Int) extends Tree
```
Hint: a tree is either a leaf or an inner node.

```scala
def trees: Generator[Tree] = for {
  isLeaf <- booleans
  tree <- if (isLeaf) leafs else inners
} yield tree

def leafs: Generator[Leaf] = for {
  x <- integers
} yield Leaf(x)

def inners: Generator[Inner]x = for {
  left <- trees
  right <- trees
} yield Inner(left, right)

trees.generate
```

## Application: Random Testing
You know about units tests:

* Come up with some some test inputs to program functions and a ***postcondtion***.
* The postcondition is a property of the expected result.
* Verify that the program satisfies the postcondition.

***Question:*** Can we do without the test inputs?

* Yes, by generating random test inputs.

## Random Test Function
Using generators, we can write a random test function:

```scala
def test[T](g: Generator[T], numTimes: Int = 100)
		(nestedTest: T => Boolean): Unit = {
	for (i <- 0 until numTimes) {
		val value = g.generate
		assert(nestedTest(value), "test failed for " + value)
	}
	println("passed " +numTimes+" test")
}
```

Example usage:

```scala
test(pairs(lists, lists)) {
	case (xs, ys) => (xs ++ ys).length > xs.length
}
```

**Question:** Does the above property always hold?

* No, cause lists can be empty lists.

## ScalaCheck
Shift in viewpoint: Instead of writing tests, wirte ***properties*** that are assumed to hold. This idea is implemented in the `ScalaCheck` tool.

```scala
forAll { (l1: List[Int], l2: List[Int]) =>
	l1.size + l2.size == (l1 ++ l2).size
}
```
It can be used either stand-alone or as part of ScalaTest. See ScalaCheck tutorial on the cousre page.