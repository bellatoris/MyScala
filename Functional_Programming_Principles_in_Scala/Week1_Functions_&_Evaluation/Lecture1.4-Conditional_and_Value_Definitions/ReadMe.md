# Conditionals and Value Definitions

## Conditional Expressions
To express choosing between two alternatives, Scala has a conditional epxression `if-else`. It looks like a `if-else` in Java, but is used for expressions, not statements.  
Example:

```scala
def abs(x: Int) = if (x >= 0) x else -x
```

`x >= 0` is a *predicate*, of type `Boolean`.

## Boolean Expression
`Boolean` expressions b can be composed of 


```scala
true false        // Constants
!b                // Negation
b && b            // Conjunction
b || b            // Disjunction
```

## Rewrite rules for Booleans
Here an reduction rules for Boolean expressions (e is an arbitrary exrpession):

```scala
!true      -->   false
!false     -->   true
true && e  -->   e
false && e -->   false
true || e  -->   true
false || e --> e
```
Note that `&&` and `||` do not always need their right operand to be evaluted. We say, these expressions use "short-circuit evaluation".

## Value Definitions
We have seen that function parameters can be passed by value or be passed by name. The same distinction applies to definitions. The `def` form "by-name", its right side is evaluated on each use. There is also a `val`, for which is "by-value". Example:

```scala
val x = 2
val y = square(x)
```
The right-hand side of a `val` definition is evaluated at the point of the definition itself. Afterwards, the name refers to the value. For instance, `y` above refers to 4, not `square(2)`.

## Value Definitions and Termination
The difference between `val` and `def` becomes apparent when the right hand side does not terminate. Given

```scala
def loop: Boolean = loop
``` 
A definition 

```scala
def x = loop
```
is OK, but a definition

```scala
val x = loop
```
will lead to an infinte loop.

## Exercise
Write functions `and` and `or` such that for all argument expressions `x` and `y`:

```scala
and(x, y)    ==    x && y
or(x, y)     ==    x || y 
```
(do not use `||` and `&&` in your implementation)  
What are good operands to test that equalities hold?

```scala
def and(x: Boolean, y: Boolean): Boolean = 
	if (x) y
	else false
	
def or(x: Boolean, y: Boolean): Boolean = 
	if (x) true
	else y
```