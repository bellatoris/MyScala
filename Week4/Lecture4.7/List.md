# Lecture 4.7 - Lists
## Lists
* The list is a fundamental data structure in functional programming.
* A list having `x_1, ..., x_n` as elements is written `List(x_1, ..., x_n)`
* **Example**

	```scala
	val fruit 	= List("apples", "oranges", "pears")
	val nums 	= List(1, 2, 3, 4)
	val diag3 	= List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))
	val empty 	= List()
	```

* There are two important differences between lists and arrays.
	* List are immutable --- the elements of a list cannot be changed.
	* List are recursive, while arrays are flat. 