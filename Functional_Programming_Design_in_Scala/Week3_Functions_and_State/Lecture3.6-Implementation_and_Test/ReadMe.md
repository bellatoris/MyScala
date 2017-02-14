# Lecture 3.6 - Discrete Event Simulation: Implementation and Test

## The Simulation Trait
All we have left to do now is to implement the `Simulation` trait. The idea is to keep in every instance of the `Simulation` trait an ***agenda*** of actions to perform. The agenda is a list of (simulated) ***events.*** Each event consists of an action and the time when it must be produced. The agenda list is sorted in such a way that the actions to be performed first are in the beginning.

```scala
trait Simulation {
	type Action = () => Unit
	case class Event(time: Int, action: Action)
	private type Agenda = List[Event] 
	private var agenda: Agenda = List()
}
```

## Handling Time
There is also a private variable, `curtime`, that contains the current simulation time:

```scala
private var curtime = 0 
```
It can be accessed with a getter function `currentTime`:

```scala
def currentTime: Int = curtime
```
An application of the `afterDelay(delay)(block)` method inserts the task 

```scala
Event(curtime + delay, () => block)
```
into the `agenda` list at the right position.

## Implementing AfterDelay
```scala
def afterDelay(delay: Int)(block: => Unit): Unit = {
	val item = Event(currentTime + delay, () => block)
	agenda = insert(agenda, item)
}
```
The `insert` function is straightforward:

```scala
private def insert(ag: List[Event], item: Event): List[Event] = ag match {
	case first :: rest if first.time <= item.time =>
		first :: insert(rest, item)
	case _ =>
		item :: ag
}
``` 

## The Event Handling Loop
The event handling loop removes successive elements from the agenda, and performs the associated actions.

```scala
private def loop(): Unit = agenda match {
	case first :: rest =>
		agenda = rest
		curtime = first.time
		first.action()
		loop()
	case Nil =>
}
```

## The Run Method
The `run` method executes the event loop after installing an initial message that signals the start of simulation.

```scala
def run(): Unit = {
	afterDelay(0) {
		println("*** simulation started, time = " + currentTime + " ***")
	}
	loop()
}
```
## Probes
Before launching the simulation, we still need a way to examine the changes of the signals on the wires.  
To this end, we define the function `probe`.

```scala
def probe(name: String, wire: Wire): Unit = {
	def probeAction(): Unit = {
		println(s"$name $currentTime value = ${wire.getSignal}")
	}
	wire addAction probeAction
}
```

## Defining Technology-Dependent Parameters
It's convenient to pack all delay constants into their own trait which can be mixed into a simulation. For instance:

```scala
trait Prameters {
	def InverterDelay = 2
	def AndGateDelay = 3
	def OrGateDelay = 5
}

object sim extends Circutis with Parameters
```

## A Variant
An alternative version of the OR-gate can be defined in terms of AND and INV.

```scala
def orGateAlt(in1: Wire, in2: Wire, output: Wire): Unit = { 
	val notIn1, notIn2, notOut = new Wire
	inverter(in1, notIn1); inverter(in2, notIn2)
	andGate(notIn1, notIn2, notOut)
	inverter(notOut, output)
}
```

## Exercise
**Question:** What would change in the circuit simulation if the implementation of `orGateAlt` was used for OR?

* The times are different, and `orGateAlt` may also produce additional events. Because it has more components, and takes more time to stabilize.

## Summarize 
State and assignments make our mental model of computation more complicated. In particular, we lose referential transparency. On the other hand, assignments allow us to formulate certain programs in an elegant way. Example: discrete event simulation.

* Here, a system is represented by a mutable list of ***actions.***
* The effect of actions, when they're called, change the state of objects and can also install other actions to be executed in the future.