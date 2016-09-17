# Lecture 3.1 - Class Hierarchies

## Abstract Classes
* Abstract classes can contain members which are missing an implementation
* Consequently, no instances of an abstract class can be created with the operator `new`

## Class Extensions
* Let's consider implementing sets as binary trees.
* There are two types of possible trees:
	* a tree for empty set
	* a tree consisting of an integer and two sub-trees.
* Here are their implementations:

```scala
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(s: Int): Boolean
}

class Empty extends IntSet {
	def contains(x: Int): Boolean = false
	def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
} 

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
	def contains(x: Int): Boolean = 
		if (x < elem) left contains x
		else if (x > elem) right contains x
		else true
		
	def incl(x: Int): IntSet = 
		if (x < elem) new NonEmpty(elem, left incl x, right)
		else if (x > elem) new NonEmpty(elem, left, right incl x)
		else this
}
```
**The implementation of binary tree is still purely functional, there's np mutations.**

* **Include: create a new tree that contains the previous element of the tree**
*  **But the old version of the data structure is still maintained, it doesnt't go away.** 
	* **persistent data structure**


## Terminology
* `Empty` and `NonEmpty` both `extend` the class `IntSet`.
* This implies that the types `Empty` and `NonEmpty` conform to the type `IntSet`

> an object of type `Empty` or `NonEmpty` can be used wherever an object of type `IntSet` is required.

### Base Classes and Subclasses
* `IntSet` is called the **superclass** of `Empty` and `NonEmpty`.
* `Empty` and `NonEmpty` are **subclasses** of `IntSet`.
* In Scala, any user-defined class extends another class.
* If no superclass is given, the standar class `Object` in the Java package `java.lang` is assumed.
* The direct or indirect superclasses of a class `C` are called **base classes* of `C`.
* SO, the base classes of `NonEmpty` are `IntSet` and `Object`.

### Implemetation and Overriding
* The definition of `contains` and `incl` in the classes `Empty` and `NonEmpty` **implement** the abstract functions in the base trait `IntSet`.
* It is also possible to **redefine** an exisiting, non-abstract definition in a subclass by using **override**.

## Object Definitions
* In the `IntSet` example, one could argue that there is really only a single empty `IntSet`.
* So it seems overkill to have the user create many instances of it.
* We can express this case better with an **object** definition:

```scala
objet Empty extends IntSet {
	def contains(x: Int): Boolean = false
	def incl(x: Int): IntSet = new NonEmpty(x, Emtpy, Empty)
}
```
* This defines a **singleton** object names `Empty`
* No other `Empty` instances can be (or need to be) created.
* Singleton objects are values, so `Empty` evaluates to itself.

## Programs
* So far we have excuted all **Scala code** from the REPL or the worksheet.
* But it is also possible to create standalone applications in Scala.
* Each such applications contains an object with a `main` method.
* For instacne, here is the " Hello World" program in Scala.

```scala
object Hello {
	def main(args: Array[String]) = println("hello, world!")
}
```
Once this program is complied you can start it from the command line with

```bash
> scala Hello
```

### Example: implements the union operation
* It is very hard quiz...
* I need to more time to understand it

## Dynamic Binding
* Object-oriendted lanuages (including Scala) implement **dynamic method dispatch**.
* This means that the code invoked by a method call depends on the runtime type of the object that contains the method.
* **Example**
	* `Empty cotains 1`
	* `[1/x][Empty/this] false`
	* `false`

* Another evaluation using `NonEmpty`:
	* `(new NonEmpty(7, Empty, Empty)) contains 7)`
	* `[7/elem][7/x][new NonEmpty(7, Empty, Empty)/this]`
	
		```scala
		if (x < elem) this.left contains x
		else if (x > elem) this.right contains x
		else true
		```	 
	* `true`

## Something to Ponder
* Dynamic dispatch of methods is anallgous to calls to higher-order fucntions.
* *Quetions*:
	* Can we implement one concept in terms of the other?
		* Objects in terms of higher-order functions?
		* Higher-order functions in terms of objects?  
