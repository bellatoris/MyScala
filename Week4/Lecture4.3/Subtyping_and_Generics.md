# Lecture 4.3 - Subtyping and Generics

## Polymorphism
* Two principal forms of polymorphism:
	* subtyping
	* generics

* In this session we will look at their interactions.
* Two main areas:
	* bounds
	* variance

## Type Bounds
* Consider the mehtod `assertAllPos` which
	* takes an `InSet`
	* returns the `InSet` itself if all its elements are positive
	* throws an exception otherwise

* What would be the best type you cna give to `assertAllPos`? Maybe: 	  
```scala
	def assertAllPos(s: IntSet): IntSet
```

* In most situations this is fine, but can one be more precise?
	* `assertAllPos(Empty) = Empty`
	* `assertAllPos(NonEmpty(...)) = NonEmpty(...) or throw Exception`

## Type Bounds
* One might want to express that `assertAllPos` takes `Empty` sets to `Empty` sets and `NonEmpty` sets to `NonEmpty` sets.
* A way to express this is:

```scala
	def assertAllPos[S <: IntSet](r: S): S = ...
```

* Here, `"<: IntSet"` si an *upper bound* of the type parameter `S:`
* It means that `S` can be instantiated only to types that conform to `IntSet`
* Generally, the notation
	* `S <: T` means: `S` *is a subtype of* `T`, and
	* `S >: T` means: `S` *is a supertype of* `T`, or `T` *is a subtype of* `S`.

Hmm  `NonEmpty` and `Empty` maybe extend `IntSet`. So If one class extends the other class, the extender is smaller than extendee.

## Lower Bounds
* You can also use a lower bound for a type variable/
* **Example**

```scala
	[S >: NonEmpty]
```

* introduces a type parameter `S` that can range only over *supertypes* of `NonEmpty`.
* So `S` could be one of `NonEmpty`, `Intset`, `AnyRef`, of `Any`.

## Mixes Bounds
* Finall, it is also possible to mix a lower bound with an upper bound.
* For instance,

```scala
	[S >: NonEmpty <: IntSet]
```
* would restrict `S` any type on the interval between `NonEmpty` and `IntSet`

## Covariance
* There's another interaction betwwen subtyping and type parameters we need to consider. Given:

```scala
	NonEmpty <: IntSet
```

is 

```scala
	List[NonEmpty] <: List[IntSet]
```

* Intuitively, this makes sense A list of non-empty sets is a special case of a list of arbitrary sets.
* We call types for which this relationship holds *covarient* because their subtyping relationship varies with the type parameter.
* Dos covariance make sense for all types, not just for `List`?

## Arrays
* For perspective, let's look at arrays in Java (and C#).
* Reminder:
	* An array of `T` elements is written `T[]` in Java.
	* In Scala we use parameterized type syntax `Array[T]` to refer to the same type.

* Arrays in Java are covarient, so one would have:

```scala
	NonEmpty[] <: IntSet[]
```
## Array Typing Problem 
* But covariant array typing causes problems.
* To see why, consider the Java code below.

```scala
	NonEmpty[] a = new NonEmpty[]{new NonEmpty(1, Empty, Empty)}
	IntSet[] b = a
	b[0] = Empty
	NonEmpty s = a[0]
```

* It looks like we assigned in the last line an `Empty` set to a variable of type `NonEmpty`!
* What went wrong?
	* So Java needs to store in every array a *type tag* that reflects what type this array was created. 
* Why did the designers of Java do it in the end?
	* They wanted to be able to write a method such as sort that would work for and array.
	* Later Java five, it is designed by using generic type

## The Liskov Substitution Priciple
* The following principle, stated by Barbara Liskov, tells us when a type can be a subtype of another

> If `A <: B`, then everything one can to do with a value of type `B` one should also be able to do with a value of type `A`.

[ The actual definition Liskov used is a bit more formal. It says:

> Let `q(x)` be a property provable about object `x` of type `B`. Then `q(y)` should be provable for object `y` of type `A` where `A <: B`.

]
 
## Exercise
* The problematic array example would be written as follows in Scala:

```scala
	val a: Array[NonEmpty] = Array(new NonEmpty(1, Empty, Empty))
	val b: Array[IntSet] = a
	b(0) = Empty
	val s: NonEmpty = a(0)
```

* When you try out this example, what do you observe?
	1. A type error in line 1
	2. A type error in line 2
	3. A type error in line 3
	4. A type error in line 4
	5. A program that compiles and throws an exception at run-time
	6. A program that compiles and runs without exception
	
* Answer is 2.
	* Error command:
	
	```scala
	<console>:10:
	ßerror: type mismatch;
 	found   : Array[NonEmpty]
 	required: Array[IntSet]
	Note: NonEmpty <: IntSet, but class Array is invariant in type T.
	You may wish to investigate a wildcard type such as `_ <: 	IntSet`. (SLS 3.2.10)
   val b: Array[IntSet] = a
   ```
* Why?
	* `Array[NonEmpty]` is not subtype of `Array[IntSet]`