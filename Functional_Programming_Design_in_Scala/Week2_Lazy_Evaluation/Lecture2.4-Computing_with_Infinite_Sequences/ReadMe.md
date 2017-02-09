# Lecture 2.4 - Computing with Infinite Sequence

## Infinite Stremas
You saw that all elements of a stream except the first one are computed only when they are needed to produce a reuslt. This opens up the possibility to define infinite streams! For instance, here is the stream of all integers strating from a given number:

```scala
def from(n: Int): Stream[Int] = n #:: from(n + 1)
```
The stream of all natural numbers:

```scala
val nats = from(0)
```
The stream of all multiples of 4:

```scala
nats map (_ * 4)
```

## The Sieve of Eratosthenes
The Sieve of Eratosthenes is an ancient technique to calculate prime numbers. The idea is as follows:

* Start with all integers from 2, the first prime number.
* Eliminate all multiples of 2.
* The first element of the resulting list is 3, a prime number.
* Eliminate all multiples of 3.
* Iterate forever. At each step, the first number in the list is a prime number and we eliminate all its multiples.

## Back to Square Roots
Our previous algorithm for square roots always used a `isGoodEnough` test to tell when to terminate the iteration. With streams we can now express the concept of a converging sequence without having to worry about when to terminate it:

```scala
def sqrtStream(x: Double): Stream[Double] = {
	def improve(guess: Double) = (guess + x / guess) / 2
	lazy val guesses: Stream[Double] = 1 #:: (guesses map improve)  // it's so weird
	guesses
}
```

## Termination
We can add `isGoodEnough` later.

```scala
def isGoodEnough(guess: Double, x: Double) = 
	math.abs((guess * guess - x) / x) < 0.0001
	
sqrtStream(4) filter (isGoodEnough(_ , 4))
```

## Exercise:
Consider two ways to express the infinite stream of multiples of a given number N:

```scala
val xs = from(1) map (_ * N)

val ys = from(1) filter(_ % N == 0)
```
Which of the two streams generates its results faster?

* first one, because it doesn't generate unnecessary stream elements