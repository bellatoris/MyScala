# Lecture 1.2 - Elements of Programming

## Elements of Programming
Every non-tivial programming language provides:

* primitive expressions representing the simplest elements
* ways to *combine* expressions
* ways to *abstract* expressions, which introduce a name for an expression by which it can then be referred to.

## The Read-Eval-Print Loop
Functional programming is a bit like using a calculator. An interactive shell (or REPL, for Read-Eval-Print-Loop) lets one write expressions and responds with their value.  
The Scala REPL can be started by simply typing

`> scala`

## Evaluation
A non-primitive expression is evaluated as follows:

1. Take the leftmost operator
2. Evaluate its operands (left before right)
3. Apply the operator to the operands

A name is evaluated by replacing it with the right hand side of its definition. The evaluation process stops once it returns in a value.

## Parameters 
Definitions can have parameters. For instance:

```scala
def square(x: Double) = x * x
def sumOfSquare(x: Double, y: Double) = square(x) + square(y)
```

## Parameters and Return Types
Function parameters come with their type, which is given after a colon 

	```scala
	def power(x: Double, y: Int): Double = ...
	```
	
If a return type is given, it follows the parameter list. Primitive types are as in Java, but are written capitalized, e.g:

* `Int` 32-bit integers
* `Double` 64-bit floating point numbers
* `Boolean` boolean values `true` and `false`

## Evaluation of Fucntion Applications
Applications of parameterized functions are evaluated in a similar way as operators:

1. Evaluate all function arguments, from left to right
2. Replace the function application by the function's right-hand side, and, at the same time
3. Replace the formal parameters of the function by the actual arguments.

## The substitution model
This scheme of expression evaluation is called the *substitution model.* The idea underlying this model is that all evaluation does is *reduce an expression to a value.* It can be applied to all expressions, as long as they have no **side effect.** The substitution model is formalized in the **lamba-calculus**, which gives a foundation for functional programming.

```scala
sumOfSquare(3, 2+2)
sumOfSquare(3, 4)
square(3) + square(4)
3 * 3 + square(4)
9 + square(4)
9 + 4 * 4 
9 + 16
25
```

## Termination
Does every expression reduce to a value (in a finite number of steps)?  
No. Here is a counter-example

```scala
def loop: Int = loop
loop -> loop -> loop -> ...
```

## Changing the evaluation strategy
The interpreter reduces function arguments to values before rewriting the function application. One could alternatively apply the function to unreduced arguments. For instance:

```scala
sumOfSquares(3, 2+2)
square(3) + square(2+2)
3 * 3 + square(2+2)
9 + square(2+2)
9 + 4 * (2+2)
9 + 4 * 4
25
```

## Call-by-name and call-by-value
The fisrt evaluation strategy is known as *call-by-value*, the second is known as *call-by-name.* Both strategies reduce the same final values as long as

* the reduced expression consists of **pure functions**, and 
* both evaluations **terminate**.

Call-by-value has the advantage that it evaluates every function argument only once.  
Call-by-name has the advantage that a function argument is not evaluated if the corresponding parameter is unused in the evalution of the function body.

Question: Say you are given the following function definition:

```scala
def test(x: Int, y: Int) = x * x
```
For each of the following function applications, indicate which evaluation startegy is fastest (has the fewest reduction steps)

```scala
test(2, 3)      same
test(3+4, 8)    CBV
test(7, 2*4)    CBN
test(3+4, 2*4)  CBV
```
