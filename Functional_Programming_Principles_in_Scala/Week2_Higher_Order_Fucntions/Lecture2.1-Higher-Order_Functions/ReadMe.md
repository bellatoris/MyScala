# Higher-Order Functions
## Higher-Order Functions
Functional languages treat functions as *first-class values.* This means that, like any other value, a function can be passed as a parameter and returned as a result. This provides a flexible way to compose programs. Functions that take other functions as parameters or that return functions as result are called *higher-order functions.*

## Example:
Take the sum of the integers between a and b:

```scala
def sumInts(a: Int, b: Int): Int = 
	if (a > b) 0 else a + sumInts(a + 1, b)
```
Take the sum of the cubes of all the integers between a and b:

```scala
def cube(x: Int): Int = x * x * x

def sumCubes(a: Int, b: Int): Int =
	if (a > b) 0 else cube(a) + sumCubes(a + 1, b)
```
Take the sum of the factorials of all the integers between a and b:

```scala
def fact(n: Int): Int = 
	if (n == 0) 1 
	else n * fact(n - 1)

def sumFactorials(a: Int, b: Int): Int = 
	if (a > b) 0 else fact(a) + sumFactorials(a + 1, b)
```
These are special cases of `sigma(n=a to b)(f(n))` for different values of `f`.  
Can we factor out the common pattern?

## Summering with Higher-Order Functions
Let's define:

```scala
def sum(f: Int => Int, a: Int, b: Int): Int = 
	if (a > b) 0
	else f(a) + sum(f, a + 1, b)
```
We can then write:

```scala
def sumInts(a: Int, b: Int)        = sum(id, a, b)
def sumCubes(a: Int, b: Int)       = sum(cube, a, b)
def sumFactorials(a: Int, b: Int)  = sum(fact, a, b)
```
where 

```scala
def id(x: Int): Int = x
def cube(x: Int): Int = x * x * x
def fact(x: Int): Int = if (x == 0) 1 else fact(x - 1)
```

## Function Types
the type `A => B` is the type of a ***function*** that takes an argument of type `A` and returns a result of type `B`. So, `Int => Int` is the type of functions that map integers to integers.

## Anonymous Functions
Passing functions as parameters leads to the creation of many small functions.

> Sometimes it is tedious to have to define (and name) these functions using `def`.

Compare to strings: We do not need to define a string using `def`. Instead of 

```scala
def str = "abc"; println(str)
```
We can directly write

```scala
println("abc")
```
because strings exists as *literals.* Analogously we would like function literals, which let us write a function without giving it a name. These are called *anonymous functions.*

## Anonymous Function Syntax
**Example:** A function that raises its argument to a cube:

```scala
(x: Int) => x * x * x
```
Here, `(x: Int)` is the ***parameter*** of the function, and `x * x * x` is a ***body.***

> The type of the parameter can be omitted if it can be inferred by the compiler from the context.

If there are several parameters, they are separated by commans:

```scala
(x: Int, y: Int) => x + y
```

## Anonymous Functions are Syntatic Sugar
An anonymous function `(x_1: T_1, ..., x_n: T_n) => E` can always be expressed using `def` as follows: `def f(x_1: T_1, ..., x_n: T_n) = E; f` where `f` is an arbitrary, fresh name (that's not yet used in the program).

> One can therefore say that anonymous functions are *syntatic sugar.*

## Summation with Anonymous Functions
Using anonymous functinos, we can write sums in a shorter way:

```scala
def sumInts(a: Int, b: Int) = sum(x => x, a, b)
def sumCubes(a: Int, b: Int) = sum(x => x * x * x, a, b)
```

## Exercise
1. Write a `product` function that calculates the product of the values of a function for the points on a given interval.
2. Write `factorial` in terms of `product`.
3. Can you write a more general function, which generalizes both `sum` and `product`?

```scala
def product(f: Int => Int, a: Int, b: Int)
	if (a > b)  1
	else f(a) * product(f, a + 1, b)
	
def factorial(n: Int) = product(x => x, 1, n) 
```