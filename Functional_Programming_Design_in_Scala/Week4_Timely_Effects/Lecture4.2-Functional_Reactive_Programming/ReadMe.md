# Lecture 4.2 - Functional Reactive Programming

## What is FRP?
Reactive programming is about reacting to sequences of *events* that happen in *time*.  
Functional view: Aggregate an event sequence into a *signal*.

* A signal is a value that changes over time. 
* It is represented as a function from time to the value domain.
* Instead of propagating updates to mutable state, we define new signals in terms of existing ones.

## Example: Mouse Positions
***Event-based view:*** Whenever the mouse moves, and event 

```scala
MouseMoved(toPos: Position)
```
is fired.

***FRP view:*** A signal

```scala
mousePosition: Signal[Position]
```
which at any point in time represents the current mouse position.

## Oirigns of FRP
FRP started in 1997 with the paper Functional Reactive Animation by Conal Elliott and Paul Hudak and the Fran library. There have been many FRP systems since, both standalone languages and embedded  libraries. Some examples are: Flapjax, Elm, Bacon.js, React4J. Event streaming dataflow programming systems such as Rx (which we will see in two weeks), are related but the term FRP is not commonly used for them. We will introduce FRP by means of a minimal class, `frp.Signal` whose implementation is explained at the end of this module. `frp.Signal` is modelled after `Scala.react`, which is described in the paper Deprecating the Observer Pattern.

## Fundamental Signal Operations 
There are two fundamental operations over signals.

1. Obtain the value of the signal at the current time. In our library this is expressed by `()` application.
	
	```scala
	mousePosition()    // the current mouse position
	```
	
2. Define a signal in terms of other signals. Is our library, this is expressed by the `Signal` constructor.

	```scala
	def inReactangle(LL: Position, UR: Position): Signal[Boolan] = 
		Signal {
			val pos = mousePosition()
			LL <= pos && pos <= UR
		}
	```
	
## Constant Signals
The `Signal(...)` syntax can also be used to define a signal that has always the same value:

```scala
val sig = Signal(3)    // the signal that is always 3
```

## Time-Vayring Signals
How do we define a signal that varies in time?

* We can use externally defined signals, such as `moustPosition` and map over them.
* Or we can use a `Var`.

## Variable Signals
Values of type `Signal` are immutable. But our library also defines a subclass `Var` of `Signal` for signals that can be changed. `Var` provides an "update" operation, which allows to redefine the value of a signal from the current time on.

```scala
val sig = Var(3)
sig.update(5)     // From now on, sig returns 5 instead of 3
```

## Aside: Update Syntax
In Scala, calls to `update` can be written as assigments. For instance, for an array `arr`

```scala
arr(i) = 0
```
is translated to 

```scala
arr.update(i, 0)
```
which calls an `update` method which can be thought of as follows:

```scala
class Array[T] {
	def update(idx: Int, value: T): Unit
	...
}
```

Generally, an indexed assignment like `f(E_1, ..., E_n) = E` is translated to `f.update(E_1, ..., E_n, E)`. This works also if n = 0: `f() = E` is shorthand for `f.update(E)`. Hence,

```scala
sig.update(5)
``` 
can be abberivated to 

```scala
sig() = 5
```

## Signals and Variables
Signals of type `Var` look a bit like mutable variables, where 

```scala
sig()
```
is dereferencing, and 

```scala
sig() = newValue
```
is update.  
But there's a crucial difference:  
We can *map* over signals, which gives us a relation between two signals that is maintained automatically, at all future points in time. No such mecahnism exists for mutable variables: we have to propagate all updates manually.

```scala
a() = 2
b() = 2 * a()
a() = 3          // b is automatically updated to 6.
```

## Example
Repeat the `BankAccount` example of last section with signals. Add a signal `balance` to `BankAccount`s. Define a function `consolidated` which produces the sum of all balances of a given list of accounts. What saving were possible compated to the publish / subscribe implementation?

## Signals and Variables (2)
Note that there's an important difference between the variable assingment 

```scala
v = v + 1
```
and the signal update

```scala
s() = s() + 1
```
In the first case, the *new* value of `v` becomes the *old* valud of `v` plus `1`. In the second case, we try to define a signal `s` to be *at all points in time* one larger than itself. This obviously makes no sense!

## Exercise
Consider the two code fragments below

```scala
val num = Var(1)
val twice = Signal(num() * 2)
num() = 2
```

```scala
var num = Var(1)
val twice = Signal(num() * 2)
num = Var(2)
```
Do they yield the same final value for `twice()`?

* No, in second, the `twice` is `2` forever.