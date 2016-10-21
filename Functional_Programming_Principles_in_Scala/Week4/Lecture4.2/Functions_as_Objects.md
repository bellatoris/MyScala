# Lecture 4.2 - Functions as Objects
Function value and Function type, What's difference of them?

* Function type: It can be described by input type of function and output type of function
* Function value: Instance(value) of function type.

## Functions as Objects
* We habe seen that Scala's numeric types ans the `Boolean` type can be implemented like normal classes.
* But what about functions?
* In fact **functions values** *are* treated as **objects** in Scala.
* The **function type** `A => B` is just an abbreviation for the class `scala.Function1[A, B]`, which is roughly defined as follows.

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
or, shorter, using *anonymous class syntax:*

```scala
new Function1[Int, Int] {
  def apply(x: Int) = x * x
}
```
It's same with Java's anonymous function.


## Expansion of Functions Calls
* A function call, such as `f(a, b)`, where `f` is a **value** of some **class type**, is expanded to 

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
Functions are objects. The method `apply` would that, by itself, be an object? That can't very well work. Because if apply was an object, it would be an instance of this function class, which would be, have an apply method, which would be an object. We would get an infinite expansion.
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

### Conclusion:
Method is not a function. So if we want to put the method where a Function type is expected, we need to convert the method to function value.