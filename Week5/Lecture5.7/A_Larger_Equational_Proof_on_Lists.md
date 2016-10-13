# Lecture 5.7 - A Larger Equational Proof on Lists

## A Law of Reverse
* For more difficult example, let's consider the `reverse` function.
* We pick its inefficient definition, because its more amenable to equational proofs:

	```scala
	Nil.reverse = Nil							// 1st clause
	(x :: xs).reverse = xs.reverse ++ List(x)	// 2nd clause
	```

* We'd like to prove the following proposition

	```scala
	xs.reverse.reverse = xs
	``` 
	
## Proof
* By induction on `xs`. The base case is easy:

	```scala
	Nil.reverse.reverse
	= Nil.reverse							// by 1st clause of reverse
	= Nil									// by 1st clause of reverse
	```
	
* For the induction step, let's try:

	```scala
	(x :: xs).reverse.reverse
	= (xs.reverse ++ List(x)).reverse		// by 2nd clause of reverse
	```
	
* We can't do anything more with the expression, therefore we turn to the right-hand side:

	```scala
	x :: xs
	= x :: xs.reverse.reverse				// by induction hypothesis
	```

* Both side are simplified in different expressions.

## To Do
* We still need to show:

	```scala
	(xs.reverse ++ List(xs)).reverse = x :: xs.reverse.reverse
	```

* Trying to prove it directly by induction doesn't work.
* We must instead try to *generalize* the equation. For *any* list *ys* (`xs.reverse => ys`),

	```scala
	(ys ++ List(x)).reverse = x :: ys.reverse
	```

* This equation can be proved by a second induction argument on `ys`.

## Auxiliary Equation, Base Case
* `ys = Nil`

	```scala
	(Nil ++ List(x)).reverse				// to show: = x :: Nil.reverse
	= List(x).reverse						// by 1st clause of ++
	= (x :: Nil).reverse					// by definition of List
	= Nil ++ (x :: Nil)						// by 2nd clause of reverse
	= x :: Nil								// by 1st clause of ++
	= x :: Nil.reverse						// by 1st clause of reverse
	```
	
## Auxiliary Equation, Inductive Step
* `ys = any`

	```scala
	((y :: ys) ++ List(x)).reverse			// to show: x :: (y :: ys).reverse
	= (y :: (ys ++ List(x))).reverse		// by 2nd clause of ++
	= (ys ++ List(x)).reverse ++ List(y)	// by 2nd clause of reverse
	= (x :: ys.reverse) ++ List(y) 			// by the induction hypothesis
	= x :: (ys.reverse ++ List(y))			// by 1st clause of ++
	= x :: (y :: ys).reverse				// by 2nd clause of reverse
	```
	
* This establishes the auxiliary equation, and with it the main proposition.

## Exercise (Open-Ended, Harder)
* Prove the following distribution law for `map` over concatenation.
* For an lists `xs`, `ys`, function `f`:

	```scala
	(xs ++ ys) map f = (xs map f) ++ (ys map f)
	```

* You will need the clauses of `++` as well as the following clauses for `map`:

	```scala
	Nil map f = Nil
	(x :: xs) map f = f(x) :: (xs map f)
	```
	
