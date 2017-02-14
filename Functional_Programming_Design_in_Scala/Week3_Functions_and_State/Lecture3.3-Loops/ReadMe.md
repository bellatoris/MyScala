# Lecture 3.3 - Loops

## Loops
***Proposition:*** Variables are enough to model all imperative programs. But what about control statements like loops? We can model them using functions.  
**Example:** Here is a Scala program that uses `while` loop:

```scala
def poewr(x: Double, exp: Int): Double = {
	 var r = 1.0
	 var i = exp
	 while (i > 0) { r = r * x; i = i - 1}
	 r
}
```
In Scala, `while` is a keyword. But how could we define `while` using a function (call it `WHILE`)?

## Definitions of `while` 
The function `WHILE` can be defined as follows:

```scala
def WHILE(condition: => Boolean)(command: => Unit): Unit = 
	if (condtion) {
		command
		WHILE(condition)(command)
	}
	else ()
```
***Note:*** The condition and the command must be passed by name so that they're re-evaluated in each iteration.  
***Note:*** `WHILE` is tail recursive, so it can operate with a constant stack size.

## Exercise 
Write a function implementing a `repeat` loop that is used as follows:

```scala
REPEAT {
	command
} ( condtion )
```
It should execute `command` one or more times, until `condition` is true.  
The `REPEAT` function starts like this:

```scala
def REPEAT(command: => Unit)(condition: => Boolean): Unit = {
	command 
	if (condition) ()
	else REPEAT(command)(condition)
}
```

## Exercise (open-ended)
It it also possible to obtain the following syntax?

```scala
REPEAT {
	command
} UNTIL ( condition )
```

```scala
def REPEAT(command: => Unit):Until= {
	command 
	new Until(command)
}

class Until(command: => Unit) {
	def UNTIL(condition: => Boolean): Unit = {
		if (condition) ()
		else {
			command
			UNTIL(condition)
		}
	}
}
```

## For-Loops
The classical `for` loop in Java can *not* be modeled simply by a higher-order fucntion. The reason is that in a Java program like

```java
for (int i = 1; i < 3; i += 1) { System.out.print(i + " "); }
```
the arguments of `for` contain the ***declaration*** of the variable `i`, which is visible in other arguments and in the body. However, in Scala there is a kind of `for` loop similar to Java's extended `for` loop:

```scala
for (i <- 1 until 3) { System.out.print(i + " ") }
```
This displays `1 2`.

## Translation of For-Loops
For-loops translate similarly to `for-expressions`, but using the `foreach` combinator instead of `map` and `flatMap`.  `foreach` is defined on collections with elements of type `T` as follows:

```scala
def foreach(f: T => Unit): Unit = 
	// apply 'f' to each element of the collection
```
**Example**

```scala
for (i <- 1 until 3; j <- "abc") println(i + " " + j)
```
translates to:

```scala
(1 until 3) foreach (i => "abc" foreach (j => println(i + " " + j)))
```