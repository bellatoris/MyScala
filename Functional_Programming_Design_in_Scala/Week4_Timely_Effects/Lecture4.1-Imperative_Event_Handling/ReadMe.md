# Lecture 4.1 - Imperative Event Handling: The Observer Pattern

## The Observer Pattern
The Observer Pattern is widely used when views need to react to changes in a model. Variants of it are also called.

* publish / subscribe
* model / view  / controller (MVC)

Views can announce themselves to the model with an operation, `subscribe`. And the model will, whenever there's change, `publish` the fact that is new information to the views. Then you can inquire the model about what the new state is and change it's presentation. 

## A Publisher Trait
```scala 
trait Publisher {
	private var subscribers: Set[Subscriber] = Set()
	
	def subscribe(subscriber: Subscriber): Unit = 
		subscribers += subscriber
		
	def unsubscribe(subscriber: Subscriber): Unit = 
		subscribers -= subscriber
	
	def publish(): Unit = 
		subscribers.foreach(_.handler(this))
}
```

## A Subscriber Trait
```scala
trait Subscriber {
	def handler(pub: Publisher)
}
```

## Observing Bank Accounts
Let's make `BankAccount` a `Publisher`

```scala
class BankAccount extends Publisher {
	private var balance =  0
	def currentBalance: Int = balance
	def deposit(amount: Int): Unit = 
		if (amount > 0) {
			balance += amount
			publish()
		}
	
	def withdraw(amount: Int): Unit =
		if (0 < amount && amount <= balance) {
			balance -= amount
			publish()
		} else throw new Error("insufficient funds")
}
```

## An Observer
A `Subscriber` to maintain the total balance of a list of accounts:

```scala
class Consolidator(observed: List[BankAccount]) extends Subscriber {
	observed.foreach(_.subscribe(this))
	
	private var total: Int = _    // uninitialized
	compute()                     // initialize total
	
	private def compute() =
		total = observed.map(_.currentBalance).sum
		
	def handler(pub: Publisher) = compute()
	
	def totalBalance = total 
}
```

## Observer Pattern, The Good
* Decouples view from state
* Allows to have varying number of views of a given state
* Simple to set up

## Observer Pattern, The Bad
* Forces imperative style, since handlers are `Unit`-typed
* Many moving parts that need to be co-ordinated
* Concurrency makes things more complicated
* Views are still tightly bound to one state; view update happens immediately.
	
To quantify (Adobe presentation from 2008): 

* 1 / 3rd of the  code in Adobe's desktop applicatons is devoted to event handling.
* 1 / 2 of the bugs are found in the code.

## How to Improve?
The rest of this course will explore different ways in which we can improve on the imperative view of reactive programming embodied in the observer pattern.. 