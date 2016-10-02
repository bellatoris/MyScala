# Lecture 4.4 - Variance
## Variance
* You have been seen the previoud session that some types should be covariant `(List)` whereas others `(Array)` should not.
* Roughly speaking, a type that accepts mutations of its elements should not be covariant. 
* But immutable types can be covariant, if some conditions on methods are met.
	* `List`: immutable
	* `Array`: mutable

## Definition of Variance
* Say `C[T]` is a parameterized type and `A`, `B` are types such that `A <: B`.
* In general, there are *three* possible relationships between `C[A]` and `C[B]`:

```scala
	C[A] <: C[B]										// C is covariant
	C[A] >: C[B]	   									// C is contravariant
	neither C[A] nor C[B] is a subtype of the other 	// C is nonvariant
```

* Scala lets you declare the variance of a type by annotating the type parameter:

```scala
	class C[+A] { ... }									// C is covariant
	class C[-A] { ... }									// C is contravariant
	class C[A] { ... }									// C is nonvariant
```

## Exercise
* Say you have two function types:

```scala
	type A = IntSet => NonEmpty
	type B = NonEmpty => IntSet
```

* According to Liskov Substitution Principle, which of the following should be true?
	1. `A <: B`
	2. `B <: A`
	3. `A` and `B` are unrelated.

* Answer: 1. type `A` is subtype of `B`
	* type `B` expects `NonEmpty` and returns type `IntSet`
	* type `A` expects `IntSet` which is can be `NonEmpty` and returns type `NonEmpty` which is subtype of `IntSet`
	* So type `A` satisfies the same contract as type `B`

## Typing Rules for Functions
* Generally, we have the following rule for subtyping between function types:
* If `A2 <: A1` and `B1 <: B2` then 

	```scala
	A1 => B1 <: A2 => B2
	``` 

## Function Trait Declaration
* So functions are *contravariant* in their argument type(s) and *covariant* in their result type.
* This leads to the following revised definition of the `Function1` trait:

```scala
	package scala
	trait Function[-T, +U] {
		def apply(x: T): U
	}
```

## Variance Checks
* We have seen in the array example that the combination of covariance with certain operations is unsound.
* In this case the problematic operation was the update operation on an array.
* If we turn `Array` into a class, and update into a method, it would look like this:

```scala
	class Array[+T] {
		def update(x: T) ...
	}
```

* The problematic combination is
	* the covariant type parameter `T`
	* which appears in parameter position of the method update.

* The Scala compiler will check that there are no problematic combinations when compiling a class with variance annotations.
* Roughly,
	* *covariant* type parameters can only appear in method results.
	* *contravariant* type parameters can only appear in method parameters.
	* *invariant* type parameters can appear anywhere (nonvariant?)

* The precise rules are a bit more involved, fortunately the Scala compiler perfoms them for us.

## Variance and Lists
* Let's get back to the previous implementation of lists.
* One shortcoming was that `Nil` had to be a class, whereas we would prefer it to be an object (after all, there is only one empty list.)
* Can we change that?
* Yes because we ca make `List` covariant.

## Making Classes Covariant
* Sometimes, we have to put in bit of work make a class covariant.
* Consider adding a prepend method to `List` which prepends a given element, yielding a new list.
* A first implementation of prepend could look like this:

```scala
	trait List[+T] {
		def prepend(elem: T): List[T] = new Cons(elem, this)
	}
```

* But that does not work!

## Excercise
* Why does the following code not type-check?

```scala
	trait List[+T] {
		def prepend(elem: T): List[T] = new Cons(elem, this)
	}
```

* Possible answers:
	1. prepend turns `List` into a mutable class.
	2. prepend fails variance checking.
	3. prepend's right-hand side contains a type error. 

* Answer: 2. Covariant type `T` occurs in contravariant position.
	* So the variance checking rule, which was actually invented to prevent mutable operations in covariant classes, also rules out something like this which doesn't involve any mutability at all. 
	* Do you think that's a mistake of the rules or is there are some deep wisdom to the rules nevertheless?

## Prepend Violates LSP
* Indeed, the compiler is right to throw out `List` with `prepend`, because it violates the Liskov Substitution Principle:
* Here's something one can do with a list `xs` of type `List[IntSet]`:

```scala
	xs.prepend(Empty)
```

* But the same operation on a list `ys` of type `List[NonEmpty]` would lead to a type error:

```scala
	ys.prepend(Empty)
				^ type mismatch
				required: NonEmpty
				found: Empty
```

* So `List[NonEmpty]` cannot be a subtype of `List[IntSet]`

## Lower Bounds
* But `prepend` is a natural method to have on immutable lists!
* **Question**: How can we make it variance-correct?
* We can use a *lower bound*:
	
	```scala
	def prepend[U >: T](elem: U): List[U] = new Cons(elem, this)
	```

* This passes variance checks, because:
	* covariant type parameters may appear in lower bounds of method type parameters
	* contravariant type parameters may appear in upper bounds of method

## Exercise
* Implement `prepend` as shown in trait `List`.

	```scala
	def prepend[U >: T](elem: U): List[U] = new Cons(elem, this)
	```
	
* What is the result type of this function:

	```scala
	def f(xs: List[NonEmpty], x: Empty) = xs prepend x	?
	```
	
*  Possible answers:
	1. does not type check
	2. `List[NonEmpty]`
	3. `List[Empty]`
	4. `List[IntSet]`
	5. `List[Any]`

* Answer: 3.    
	* `IntSet` is a supertype of both `Empty` and `NonEmpty`. So the `Empty` can occur where `IntSet` needs.
	*  `T` is `NonEmpty`. And `U` is `Empty`. But the `Empty` is not supertype of `NonEmpty`.
	*  So what the type inferencer would choose instead is the next higher type, `IntSet`. 
	*  Therefore, `U` is `IntSet`.
