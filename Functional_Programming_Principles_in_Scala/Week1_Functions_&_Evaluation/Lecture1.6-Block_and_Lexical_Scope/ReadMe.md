# Blocks and Lexical Scope
## Nested functions
It's a good fucntional prorgramming style to split up a task into many small fucntions. But the names of functions like `sqrtIter`, `improve` and `isGoodEnough` matter only for the *impelementation* of `sqrt`, not only for its *usage*. Normally we would not like users to access these functions directly. We can achieve this and at the same time avoid "name-space pollution" by putting the auxiliary functions inside `sqrt`.

## The `sqrt` Fucntion, Take 2

```scala
def sqrt(x: Double) = {
	def sqrtIter(guess: Double, x: Double): Double = 
		if (isGoodEnough(guess, x)) guess
		else sqrtIter(improve(guess, x), x)
		
	def imporve(guess: Double, x: Double) = 
		(guess + x / guess) / 2
		
	def isGoodEnough(guess: Double, x: Double) = 
		abs(square(guess) - x) < 0.001
		
	sqrtIter(1.0, x)
}
```

## Blocks in Scala
A bloc is delimited by braces `{ ... }`.

```scala
{ val x = f(x)
 	x * x 
}
```
It contains a sequence of definitions or expressions. The last element of a block is an expression that defines its value. This return expression can be preceded by auxiliary definitions. Blocks are themselve expressions; a block may appear everywhere an expression can.

## Blocks and Visibility
```scala
val x = 0
def f(y: Int) = y + 1
val result = {
	val x = f(3)
	x * x
}
```
The definitions inside a block are only visible from within the block. The definitions inside a block *shadow* definitions of the same names outside the block.

## Exercise: Scope Rules
Question: What is the value of `result` in the following program?

```scala
val x = 0
def f(y: Int) = y + 1
val result = { 
	val x = f(3)
	x * x
} + x

= 16
```

## Lexical Scoping
Definitions of outer blocks are visible inside a block unless they are shadowed. Therefore, we can simplify `sqrt` by eliminating redundant occurences of the `x` parameter, which means everywhere the same thing.

## The `sqrt` Fuction, Take 3
```scala
def sqrt(x: Double) = {
	def sqrtIter(guess: Double): Double = 
		if (isGoodEnough(guess)) guess
		else sqrtIter(improve(guess))
		
	def improve(guess: Double) = 
		(guess + x / guess) / 2
		
	def isGoodEnough(guess: Double) = 
		abs(square(guess) - x) < 0.001
		
	sqrtIter(1.0)
}
```

## Semicolons
In Scala, semicolons at the end of lines are in most cases optional.  
You could write 

```scala
val x = 1;
```
but most people would omit the semicolon. On the other hand, if there are more than one statements on a line, they neeed to be separated by semicolons:

```scala
val y = x + 1; y * y
```

## Semicolons and infix operators
One issue with Scala's semicolon convention is how to write expressions that span several lines. For instances

```scala
someLongExpression
+ someOtherExpression
```
would be interpreted as *two* expressions:

```scala
someLongExpressions;
+ someOtherExpressions
```

There are two ways to overcome this problem. You could write the multi-line expression in parantheses, because semicolons are never inserted inside `(...)`:

```scala
(someLongExpression
+ someOtherExpreesion)
```
Or you could wrtie the operator on the first line, because this tells the Scala compiler that the expression is not yet finished:

```scala
someLongExpression +
someOtherExpression
```

## Summary
You have seen simple elements of functional programming in Scala.

* arithmetic and boolean expressions
* conditional exrpessions if-else
* functions with recursion
* nesting and lexical scope

You have learned the difference between the call-by-name and call-by-value evaluation strategies. You have learned a way to reason about program execution: reduce expressions using the substitution model. This model will be an important tool for the coming sessions.