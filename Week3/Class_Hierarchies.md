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