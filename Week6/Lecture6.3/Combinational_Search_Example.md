# Lecture 6.3 - Combinational Search Example
## Sets
* Sets are another basic abstraction in the Scala collections.
* A `set` is written analogously to a sequence.

	```scala
	val fruit = Set("apple", "banana", "pear")
	val s = (1 to 6).toSet
	```

* Most operations on sequences are also available on sets:

	```scala
	s map(_ + 2)
	fruit filter (_.startsWith == "app")
	s.nonEmpty
	```
	
	* (see `Iterables` Scaladoc for a list of all supported operations)
	