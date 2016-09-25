# Lecture 4.2 - Functions as Objects

## Functions as Objects
* We habe seen that Scala's numeric types ans the `Boolean` type can be implemented like normal classes.
* But what about functions?
* In fact functions values *are* treated as objects in Scala.
* The function type `A => B` is just an abbreviation for the class `scala.Function1[A, B]`, which is roughly defined as follows.

```scala
package scala
trait Function1[A, B] {
	def apply(x: A): B
}
```
So functions are objects with `apply` methods
There are also traits `Function2`, `Function3`... for functions which take more parameters (currently up to 22).

## Expansion of Function Values
An anonymous function such as

```scala
(x: Int) => x * x
```
is expanded to:

```scala
{ class AnonFun extends Function1[Int, Int] {
	def apply(x: Int) = x * x
  }
  new AnonFun
}
```
It's same with Java's anonymous function.
or, shorter, using *anonymous class syntax:*

```scala
new Function1[Int, Int] {
  def apply(x: Int) = x * x
}
```

## Expansion of Functions Calls
* A function call, such as `f(a, b)`, where `f` is a value of some class type, is expanded to 

```scala
f.apply(a, b)
```
So the OO-transition of

```scala
val f = (x: Int) => x * x
f(7)
```
would be

```scala
val f = new Function1[Int, Int] {
  def apply(x: Int) = x * x
}
f.apply(7)
```
I don't get it... I need to look this part again.

## Functions and Methods
* Note that a method such as

``` scala
def f(x: Int): Boolean = ...
```
is not itself a function value.
But if `f` is used in a place where a Function type is expected, it is converted automatically to the function value

```scala
(x: Int) => f(x)
```
**This is an eta-expansion**.
or, expanded:

```scala
new Function1[Int, Boolean] {
  def apply(x: Int) = f(x)
}
```