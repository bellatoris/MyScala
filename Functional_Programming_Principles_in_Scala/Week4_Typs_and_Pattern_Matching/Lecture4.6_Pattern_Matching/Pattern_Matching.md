# Lecture 4.6 - Pattern Matching
## Reminder: Decomposition
* The task we are trying to solve is find a general and convenient way to access objects in a extensible class hierarchy.
* *Attempts seen previoulsy*:
	* *Classification and access methods*: quadratic explosion
	* *Type tests and casts*: unsafe, low-level
	* *Object-oriented decomposition*: does not always work, need to touch all classes to add a new method.

## Solution 2: Functional Decomposition with Patter Matching
* Observation: the sole purpose of test and accessor functions is to *reverse* the construction process:
	* Which subclass was used?
	* What were the arguments of the constructor?

* This situation is so common that many functional languages, Scala included, automate it.  

## Case Classes
* A *case class* definition is similar to a normal class definition, except that it is preceded by the modifier `case`. For example:

	```scala
	trait Expr
	case class Number(n: Int) extends Expr
	case class Sum(e1: Expr, e2: Expr) extends Expr
	```

* Like before, this defines a trait `Expr`, and two concrete subclasses `Number` and `Sum`.
* It also implicitly defines companion objects with `apply` methods.

	```scala
	object Number {
		def apply(n: Int) = new Number(n)
	}
	object Sum {
		def apply(e1: Expr, e2: Expr) = new Sum(e1, e2)
	}
	```

* So you cna write `Number(1)` instead of `new Number(1)`
* However, these classes are no empty. So how can we access the members?

## Pattern Matching
* *Pattern matching* is a generalization of `switch` from C/Java to class hierarchies.
* It's expressed in Scala using the keyword `match`.
* **Example**

	```scala
	def eval(e: Expr): Int = e match {
		case Number(n)  => n
		case Sum(e1, e2) => eval(e1) + eval(e2)
	}
	```
	
## Match Syntax
* Rules:
	* `match` is followed by a sequence of *cases*, `pat => expr`.
	* Each case associates an *expression* `expr` with a *pattern* `pat`.
	* A `MatchError` execption is thrown if no pattern matches the value of the selector.

## Forms of Patterns
* Patterns are constructed from:
	* *constructors*, e.g. `Number`, `Sum`
	* *variables*, e.g., n, e1, e2,
	* *wildcard patterns* _,
	* *constants*, e.g. `1`, `true`.

* Variables always begin with a lowercase letter.
* The same variable name can only appear once in a pattern, So, `Sum(x, x)` is not a legal pattern.
* Names of constants begin with a capital letter, with the exception of the reserved words `null`, `true`, `false`. 

## Evaluating Match Expressions
* An expression of the from

	```scala
	e match { case p_1 => e_1 ... case p_n => e_n }
	```

	matches the value of the selector `e` with the patterns `p_1, ..., p_n` in the order in which they are written.

* The whole match expression is rewritten to the right-hand side of the first case where the pattern matches the selector `e`.
* Refereneces to pattern variables are replaced by the corresponding parts in the selector

## What Do Pattern Match?
* A constructor pattern `C(p_1, ..., p_n)` matches all the values of type `C` (or a subtype) that have been constructed with arguments matching the patterns `p_1, ..., p_n`.
* A variable pattern `x` matches any value, and *binds* the name of the variable to this value.
* A constant pattern `c` matches values that are equal to `c` (in the sense of `==`)

## Example
* **Example**

	```scala
	eval(Sum(Number(1), Number(2)))
	->
	Sum(Number(1), Number(2)) match {
		case Number(n) => n
		case Sum(e1, e2) => eval(e1) + eval(e2)
	}
	->
	eval(Number(1)) + eval(Number(2))
	->
	Number(1) match {
		case Number(n) => n
		case Sum(e1, e2) => eval(e1) + eval(e2)
	} + eval(Number(2))
	->
	1 + eval(Number(2))
	->>
	3
 	```	
 	
## Pattern Matching and Methods
* Of course, it's also possible to define the evaluation function as a method of the base trait.
* **Example**

	```scala
	trait Expr {
		def eval: Int = this match {
			case Number(n) => n
			case Sum(e1, e2) => e1.eval + e2.eval
		}
	}
	```
* If what you do is mostly creating new subclasses, then actually the object oriented decomposition solution has the upper hand. 
	* It's very easy and very local change to just create a new subclass with an eval method.

* On the other hand, if what you do will be create lots of new methods, but the class hierarchy itself will be kept relatively stable. Then pattern matching is actually advantageous.
	* New method in the type pattern matching solution is just a local change, whether you put in the base class, or maybe even outside the class hierarchy.

## Exercise
* Write a function `show` that uses pattern matching to return the representation of a given expressions as a string.

	```scala
	def show(e: Expr): String  = this match {
		case Number(n) => n.toString
		case Sum(e1, e2) => e1.show + " + " + e2.show
	}
	```	
	
## Exercise (Optional, Harder)
* Add case classes `Var` for varibles `x` and `Prod` for products `x * y` as discussed previously.
* Change your `show` function so that it also deals with products.
* Pay attention you get operator precedence right but to use as few parentheses as possible.
* **Example**

	```scala
	Sum(Prod(Number(2), Var("x")), Var("y"))
	```
	should print as `"2 * x + y"`. But
	
	```scala
	Prod(Sum(Number(2), Var("x")), Var("y"))
	```
	should print as `"(2 + x) * y"`
	