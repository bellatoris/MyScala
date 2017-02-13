# Lecture 3.1 - Functions and State

## Functions and State 
Unitl now, our programs have been side-effect free. Therefore, the concept of ***time*** wasn't important. For all programs that terminate, any sequence of actions would have given the same results. This was also reflected in the substitution model of computation.

## Reminder: Substitution Model
Programs can be evaluated by ***rewriting.*** The most important rewrite rule covers function applications:

```scala
def f(x_1, ..., x_n) = B; ... f(v_1, ..., v_n)
= def f(x_1, ..., x_n) = B; ... [v_1/x_1, ..., v_n/x_n]B
```

## Rewriting Example:
Say you have the following two functions `iterate` and `square`:

```scala
def iterate(n: Int, f: Int => Int, x: Int) = 
	if (n == 0) x else iterate(n-1, f, f(x))
def square(x: Int) = x * x
```

Then the call `iterate(1, square, 3)` gets rewritten as follows:

```scala
-> if (1 == 0) 3 else iterate(1-1, square, square(3))
-> iterate(0, square, square(3))
-> iterate(0, square, 3 * 3)
-> iterate(0, square, 9)
-> if (0 == 0) 9 else iterate(0-1, square, square(9))
-> 9
```

## Observation:
Rewriting can be done anywhere in a term, and all rewritings which terminate lead to the same solution. This is an important result of the **lambda-calculus**, the theory behind functional programming.

Example:

```scala
if (1 == 0) 3 else itertate(1 - 1, square, square(3))

-> iterate(0, suqare, square(3)) or -> if (1 == 0) 3 else iterate(1 - 1, square, 3 * 3)

-> 9
```
This confluence result has been discovered by Church and Rosser.

## Stateful Objects
One normally describes the world as a set of objects, some of which have state that ***changes*** over the course of time. An object ***has a state*** if its behavior is influenced by its history. **Example:** a bank account has a state, because the answer to the question "can I withdraw 100 USD?" may vary over the course of the lifetime of the account.

## Implementation of State
Every form of mutable state is constructed from variables. A variable definition is written like a value definition, but with keyword `var` in place of `val`:

```scala
var x: String = "abc"
var count = 111
``` 
Just like a value definition, a variable definition associates a value with a name. However, in the case of variable definitions, the association can be changed later through an ***assignment***, like in Java:

```scala
x = "hi"
count = count + 1
```

## State in Objects
In practice, objects with state are usually represented by objects that have some variable members. **Example:** Here is a class modeling a bank account.

```scala
class BankAccount {
	 private var balance = 0
	 def deposit(amount: Int): Unit = {
	 	if (amount > 0) balance = balance + amount
	}
	 def withdraw(amount: Int): Int = {
	 	if (0 < amount && amount <= balance) {
	 		balance = balance - account
	 		balance
	 	} else throw new Error("insufficient funds")
	}
}
```
The class `BankAccount` defines a variable `balance` that contains the current balance of the account. The methods `deposit` and `withdraw` change the value of the `balance` through assignments. Note that `balance` is `private` in the `BankAccount` class, it therefore cannot be accessed from outside the class. To create bank accounts, we use the usual notation for object creation: 

```scala
val account = new BankAccount
```

## Statefulness and Variables
Remember the definition of streams (lazy sequences). Instead of using a `lazy val`, we could also implement non-empty streams using a mutable variable:

```scala
def cons[T](hd: T, tl: => Stream[T]) = new Stream[T] {
	def head = hd
	private var tlOpt: Option[Stream[T]] = None
	def tail: T = tlOpt match {
		case Some(x) => x
		case None => tlOpt = Some(tl); tail
	}
}
```

**Question:** Is the result of `cons` a stateful object?

In fact, both the yes and the no are valid responses, depending on what assumptions you make on the rest of your system. One common assumption is that streams should only be defined over purely functional computations. So the tail operation should not have a side effect. In that case, the optimization to cache the first value of `tlOpt` and reuse it on all previous calls to tail is purely a optimzation that avoids computations, but that does not have an obeservable effect outside the class of streams. So the answer would be **No**. Streams are not stateful objects. However if tail operation has side effect such as print opertaion, the answer would be cons is a stateful object.

Consider the following class:

```scala
class BankAccountProxy(ba: BankAccount) { 
	def deposit(amount: Int): Unit = ba.deposit(amount)
	def withdraw(amount: Int): Int = ba.withdraw(amount)
}
```

**Question:** Are instances of `BankAccountProxy` stateful objects?

Yes