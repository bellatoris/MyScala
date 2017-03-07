# Tail Recursion
## Review: Evaluating a Function Application
One simple rule: One evaluates a function application `f(e_1, ... e_n)`

* by evaluating the expressions `e_1, ..., e_n` resulting in the values `v_1, ... v_n` then
* by replacing the application with the body of the function `f`, in which
* the actual parameters `v_1, ..., v_n` replace the formal parameters of `f`.

## Application Rewriting Rule
This can be formalized as ***rewriting of the program itself.***

```scala
		def f(x_1, ..., x_n) = B; ... f(v_1, ..., v_n)`
->
		def f(x_1, ..., x_n) = B; ... [v_1/x_1, ..., v_n/x_n]B
```
Here, `[v_1/x_1, ..., v_n/x_n]B` means:  
The expression `B` in which all occurrences of `x_i` have been replaced by `v_i`.  
`[v_1/x_1, ..., v_n/x_n]` is called a ***substitution.***

## Rewriting example:
Consider `gcd`, the function that computes the greatest common divisor of two numbers. Here's an implementation of `gcd` using Euclid's algorithm.

```scala
def gcd(a: Int, b: Int): Int =
	if (b == 0) a else gcd(b, a % b)
```

`gcd(14, 21)` is evaluated as follows:

```scala
gcd(14, 21)->	if (21 == 0) 14 else gcd(21, 14 % 21) 
->	if (false) 14 else gcd(21, 14 % 21)
->	gcd(21, 14 % 21)->	gcd(21, 14)->	if (14 == 0) 21 else gcd(14, 21 % 14)
->	gcd(14, 7)->	gcd(7, 0)->	if (0 == 0) 7 else gcd(0, 7 % 0)-> 	7
```

## Another rewriting example:
Consider `factorial`:
	
```scala
def factorial(n: Int): Int =
	if (n == 0) 1 else n * factorial(n - 1)
	
factorial(4)->	if (4 == 0) 1 else 4 * factorial(4 - 1)->	4 * factorial(3)->	4 * (3 * factorial(2))->	4 * (3 * (2 * factorial(1)))->	4 * (3 * (2 * (1 * factorial(0)))->	4 * (3 * (2 * (1 * 1)))->	120
```

## Tail Recursion
***Implementation Consideration:*** If a function calls itself as its last action, the functions's stack frame can be reused. This is called *tail recursion*.  

> Tail recursive functions are iterative process.

In general, if the last action of a function consists of calling a function (which may be the same), one stack frame would be sufficient for both functions. Such calls are called *tail-calls.*

## Tail Recursion in Scala
In Scala, only directly recursive calls to the current function are optimized. One can require that a function is tail-recursive using a `@tailrec` annotation:

```scala
@tailrec
def gcd(a: Int, b: Int): Int = ...
```
If the annotation is given, and the implementation of `gcd` were not tail recursive, an error would be issued.

## Exercise: Tail recursion
Design a tail recursive version of factorial

```scala
def factorial(n: Int): Int = {
	@tailrec
	def nested(n: Int, fac: Int): Int = {
		if (n == 0) fac
		else nested(n - 1, fac * n)
	}
	nested(n, 1)
}
```
 