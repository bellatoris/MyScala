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
* Can we avoid the `new Generator` ... boilerplate?
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
			def generate = f(self.generate)
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
	def paris[T, U] 