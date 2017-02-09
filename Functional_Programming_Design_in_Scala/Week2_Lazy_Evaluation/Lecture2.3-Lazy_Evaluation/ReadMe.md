# Lecture 2.3 - Lazy Evaluation

## Lazy Evaluation
The proposed implementation suffers from a serious potential performance problem: If `tail` is called several times, the corresponding stream will be **recomputed** each time. It could cause up to exponential blow up in program complexity. This problem can be avoided by storing the result of the first evaluation of `tail` and re-using the stored result instead of recomputing `tail`. This optimization is sound, since in a purely functional language an expression produces the same result each time it is evaluated. We call this scheme ***lazy evalution*** (as opposed to ***by-name evaluation*** in the case where everything is recomputed, and ***strict evaluation*** for normal parameters and `val` definitions.)

## Lazy Evaluation in Scala
Haskell is a functional programming language that uses lazy evaluation by default. Scala uses strict evaluation by default, but allows lazy evaluation of value definitions with the `lazy val` form: 

```scala
lazy val x = expr
```
Then why Scala doesnt' use lazy evaluation by default? Because lazy evaluation is quite unpredictable in when computations happen and how much computations happen. In pure functional language, it shouldn't matter when computations happen. However, once you add mutable side effects, which Scala also permits, you can get into some very confusing situations. So Scala uses strict evaluation by default.

Lazy expression is resued every time except for the first one, while by-name evalutaion (`def`) always does recomputation.

## Exercise:
Consider the following program:

```scala
def expr = {
	val x = { print("x"); 1 }
	lazy val y = { print("y"); 2 }
	def z = { print("z"); 3 }
	z + y + x + z + y + x
}
expr
```
If you run this program, what gets printed as a side effect of evaluating `expr`?

* `xzyz`

## Lazy Vals and Streams
Using a lazy value for `tail`, `Stream.cons` can be implemented more efficiently:

```scala
def cons[T](hd: T, tl: => Stream[T]) = new Stream[T] {
	def head = hd
	lazy val tail = tl
	...
}
```

## Seeing it in Action
To convince ourselves that the implementation of streams really does avoid unnecessary computation, let's observe the execution trace of the expression:

```scala
(streamRange(1000, 10000) filter isPrime) apply 1 

---> (if (1000 >= 10000) empty                           // by expanding streamRange
      else cons(1000, streamRange(1000 + 1, 10000))
     .filter(isPrime).apply(1)
      
---> cons(1000, streamRange(1000 + 1, 10000))            // by evaluating if
     .filter(isPrime).apply(1)
```

Let's abbreviate `cons(1000, streamRange(1000 + 1, 10000))` to `C1`

```scala
C1.filter(isPrime).apply(1)

---> (if (C1.isEmpty) C1                                 // by expanding filter
      else if (isPrime(C1.head) cons(C1.head, C1.tail.filter(isPrime))
      else C1.tail.filter(isPrime))
     .apply(1)
     
---> (if (isPrime(C1.head) cons(C1.head, C1.tail.filter(isPrime))
      else C1.tail.filter(isPrime))                      // by eval. if
     .apply(1)
     
---> (if (isPrime(1000)) cons(C1.head, C1.tail.filter(isPrime))
      else C1.tail.filter(isPrime))                      // by eval. head
     .apply(1)
     
---> (if (false) cons(C1.head, C1.tail.filter(isPrime))  // by eval. isPrime
      else C1.tail.filter(isPrime))
     .apply(1)
     
---> C1.tail.filter(isPrime).apply(1)                    // by eval. if

---> streamRange(1001, 10000)
      .filter(isPrime).apply(1)                          // by eval. tail
```

The evalutaion sequence continues like this until:

```scala
---> streamRange(1009, 10000)
     .filter(isPrime).apply(1)
     
---> cons(1009, streamRange(1009 + 1, 10000))
     .filter(isPrime).apply(1)                          // by eval. streamRange
```

Let's abbreviate `cons(1009, streamRange(1009 + 1, 10000))` to `C2`

```scala
C2.filter(isPrime).apply(1)

---> cons(1009, C2.tail.filter(isPrime)).apply(1)

---> if (1 == 0) cons(1009, C2.tail.filter(isPrime)).head // by eval. apply
     else cons(1009, C2.tail.filter(isPrime)).tail.apply(0)
     
---> cons(1009, C2.tail.filter(isPrime)).tail.apply(0)    // by eval. if

---> C2.tail.filter(isPrime).apply(0)                     // by eval. tail

---> streamRange(1010, 10000).filter(isPrime).apply(0)    // by eval. tail
```

Assuming `apply` is defined like this in `Stream[T]`:

```scala
def apply(n: Int): T = 
	if (n == 0) head
	else tail.apply(n - 1)
```

The process continues until 

```scala
     ...
---> streamRange(1013, 10000).filter(isPrime).apply(0)

---> cons(1013, streamRange(1013 + 1, 10000))            // by eval. stremRange
     .filter(isPrime).apply(0)
```

Let `C3` be a shorthand for `cons(1013, streamRange(1013 + 1, 10000)`.

```scala
---> C3.filter(isPrime).apply(0)

---> cons(1013, C3.tail.filter(isPrime)).apply(0)        // by eval. filter

---> 1013                                                // by eval. apply
```

Only the part of the stream necessary to compute the result has been constructed.
