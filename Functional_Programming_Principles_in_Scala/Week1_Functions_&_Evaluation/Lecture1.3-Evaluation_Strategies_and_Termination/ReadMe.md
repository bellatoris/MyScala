# Evaluation Strategies and Termination

## Call-by-name, Call-by-value and termination
You know from the last moduel that the call-by-name and call-by-value evaluation strategies reduce an expression to the same value, as long as both evalutations terminate. But what if termination is not guaranteed? We have:

* If CBV evaluation of an expression *e* terminates, then CBN evaluation of *e* terminates, too.
* The other direction is not true.

## Non-termination example
Question: Find an expression that terminates under CBN but not under CBV.  
Let's define

```scala
def loop(): Int = loop
def first(x: Int, y: Int) = x
```
and consider the expression `first(1, loop)`

| Under CBN: | Under CBV: |
|:----------:|:-----------|
| `first(1, loop)`| `first(1, loop)`|

CBN terminates this expression, but the CBV can't. Because CBV try to reduce all arguments first.

## Scala's evaluation strategy
Scala normally uses call-by-value. But if type of a function parameter starts with `=>` it uses call-by-name.  
Example:

```scala
def constOne(x: Int, y: => Int) = 1
```
Let's trace the evaluation of 

```scala
constOne(1+2, loop)
-> constOne(3, loop)
-> 1
```
and 

```scla
constOne(loop, 1+2)
-> constOne(loop, 1+2)
-> constOne(loop, 1+2)
...
```