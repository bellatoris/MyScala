# Lecture 5.6 - Reasoning About Concat

## Laws of Concat
* Recall the concatenation operation `++` on lists.
* We would like to verify that concatenation is associative, and that it admits the empty list `Nil` as neutral element to the left and to the right

	```scala
	(xs ++ ys) = xs ++ (ys ++ zs)
	xs ++ Nil = xs
	Nil ++ xs = xs
	```

* Q: How can we prove properties like these?
* A: By *structural induction* on lists.

## Reminder: Natural Induction
* Recall the principle of proof by *natural induction*:
* To show a property `P(n)` for all the integers `n >= b`,
	* Show that we have `P(b)` *(base case)*,
	* for all integers `n >= b` show the *induction step*:
		* *if one has* `P(n)`, *then one also has* `P(n + 1)`

## Example
* Given:

	```scala
	def factorial(n: Int): Int = 
		if (n == 0) 1 				// 1st clause
		else n * factorial(n - 1) 	// 2nd clause
	```

* Show that, for all `n >= 4`

	```scala
	factorial(n) >= power(2, n)
	```

## Referential Transparency
* Note that a proof can freely apply reduction steps as equalities to some part of a term.
* That works because pure functional programs don't have side effects; so that a term is equivalent to the term to which it reduces.
* This principle is called *referential transparency*.

## Structural Induction
* The principle of structural induction is analogous to natural induction:
* To prove a property `P(xs)` for all lists `xs`,
	* show that `P(Nil)` holds *(base case)*,
	* for a list `xs` and some element `x`, show the *induction step*:
		* *if* `P(xs)` *holds, then* `P(x :: xs)` *also holds.*  

## Example
* Let's show that, for lists `xs, ys, zs`:

	```scala
	(xs ++ ys) ++ zs = xs ++ (ys ++ zs)
	```
	
* To do this, use structural induction on `xs`. From the previous implementation of `concat`,

	```scala
	def concat[T](xs: List[T], ys: List[T]) = xs match {
		case List() => ys
		case x :: xs1 => x :: concat(xs1, ys)
	}
	```

* distill two *defining clauses* of `++`:

	```scala
	Nil ++ ys = ys							// 1st clause
	(x :: xs1) ++ ys = x :: (xs1 ++ ys) 	// 2nd clause
	```
	
## Base Case
* **Base case:** `Nil`
* For the left-hand side we have:

	```scala
	(Nil ++ ys) ++ zs = ys ++ zs 			// by 1st clause of ++
	```
	
* For the right-hand side, we habe:

	```scala
	Nil ++ (ys ++ zs) = ys ++ zs
	```
	
* This case is therefore established.

## Induction Step: LHS
* **Induction step:** `x :: xs`
* For the left-hand side, we have:

	```scala
	((x :: xs) ++ ys) ++ zs
	= (x :: (xs ++ ys)) ++ zs 			// by 2nd clause of ++
	= x :: ((xs ++ ys) ++ zs) 			// by 2nd clause of ++
	= x :: (xs ++ (ys ++ zs))			// by induction hypothesis
	```
	
## Induction Step: RHS
* For the right hand side we have:

	```scala
	(x :: xs) ++ (ys ++ zs)
	= x :: (xs ++ (ys ++ zs)) 			// by 2nd clause of ++
	```
	
* So this case (and with it, the property) is established. 

## Exercise
* Show by induction on `xs` that `xs ++ Nil = xs`
* How many equations do you need for the inductive step?
	* Answer: 2

* Base case: `xs = Nil`

	```scala
	Nil ++ Nil
	= Nil 								// by 1st clause of ++
	```

* Induction step: `x :: xs`

	```scala
	(x :: xs) ++ Nil
	= x :: (xs ++ Nil)					// by wnd clause of ++
	= x :: xs							// by induction hypothesis
	```
	
* So this case (and with it, the property) is established. 
