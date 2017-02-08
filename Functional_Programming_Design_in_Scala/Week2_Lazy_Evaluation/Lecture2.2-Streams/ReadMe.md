# Lecture 2.2 - Streams

## Collections and Combinatorial Serach
We've seen a number of immutable collections that provide powerful perations, in particular for combinatorial serach. For instance, to find the second prime number between 1000 and 10000:

```scala 
((1000 to 10000) filter isPrime)(1)
```
This is *much* shorter than recursive alternative:

```scala
def secondPrime(from Int, to: Int, n: Int): Int = nthPrime(from, to, 2)
def nthPrime(from: Int, to: Int, n: Int): Int = 
	if (from >= to) throw new Error("no prime")
	else if (isPrime(from))
		if (n == 1) from else nthPrime(from + 1, to, n - 1)
	else nthPrime(from + 1, to, n)
```

## Performance Problem
But from a standpoint of performance, 

```scala
((1000 to 10000) filter isPrime)(1)
```
is prettey bad; it construct *all* prime numbers between 1000 and 10000 in a list, but only ever looks at the first two elements of that list. Reducing the upper bound would speed things up, but risks that we miss the second prime number all together.

## Delayed Evaluation
However, we can make the short-code efficinet by using a trick:

> *Avoid computing the tail of a sequence until it is needed for the evaluation result (which might be never)*

This idea is implemented in a new class, the `Stream`. Streams are simliar to lists, but their tail is evaluated only *on demand.*

## Defining Streams
Streams are defined from a constant `Stream.emtpy` and a constructor `Stream.cons`. For instance,

```scala
val xs = Stream.cons(1, Stream.cons(2, Stream.empty))
```
They can also be defined like the other collections by using the object `Stream` as a factory.

```scala
Stream(1, 2, 3)
```
The `toStream` method on a collection will turn the collection into a stream:

```scala
(1 to 1000).toStream    > res0: Stream[Int] = Stream(1, ?)
```

## Stream Ranges
Let's try to write a function that returns `(lo until hi).toStram` directly:

```scala
def streamRange(lo: Int, hi: Int): Stream[Int] = 
	if (lo >= hi) Stream.empty
	else Stream.cons(lo, streamRange(lo + 1, hi))
```
Compare to the same function that produces a list:

```scala
def listRange(lo: Int, hi: Int): List[Int] = 
	if (lo >= hi) Nil
	else lo :: listRange(lo + 1, hi)
```

`listRange` makes a complete list. However, `streamRange` doesn't. Until somebody wants to know all the elements in this stream, we wouldn't have the same structure as for the lists.

## Methods on Streams
`Stream` supports almost all methods of `List`. For instance, to find the second prime number between 1000 and 10000:

```scala
((1000 to 10000).toStream filter isPrime)(1)
```

## Streams Cons Operator
The one major exception is `::`. `x :: xs` always produces a list, never a stream. There is however an alternatvie operator `#::` which produces a stream. 

```scala
x #:: xs == Stream.cons(x, xs)
```

`#::` can be used in expressions as well as patterns.

## Inplementation of Streams
The implementation of streams is quite close to the one of lists. Here's the trait `Stream`:

```scala
trait Stream[+A] extends Seq[A] {
	def isEmpty: Boolean
	def head: A
	def tail: Stream[A]
	...
}
```
As for lists, all other methods can be defined in terms of these three.  
Concrete implementations of streams are defined in the `Stream` companion object. Here's a first draft:

```scala
object Stream {
	def cons[T](hd: T, tl: => Stream[T]) = new Stream[T] {
		def isEmpty = false
		def head = hd
		def tail = tl
	}
	val empty = new String[Nothing] {
		def isEmpty = true
		def head = throw new NoSuchElementException("empty.head")
		def tail = throw new NoSuchElementException("empty.tail")
	}
}
```

## Other Stream Methods
The other stream methods are implemented analogously to their list counterparts. For instance, here's `filter`:

```scala
class Stream[+T] {
	...
	def filter(p: T => Boolean): Stream[T] = 
		if (isEmtpy) this
		else if (p(head)), cons(head, tail.filter(p))
		else tail.filter(p)
}
```

## Exercise
Consider this modification of `streamRange`.

```scala
def streamRange(lo: Int, hi: Int): Stream[Int] = {
	print(lo+" ")
	if (lo >= hi) Stream.empty
	else Stream.cons(lo, streamRange(lo + 1, hi))
}
```
When you write `streamRange(1, 10).take(3).toList` what gets printed?

* `1 2 3` Because we take 3, and then make it list. So we only need to print three times.