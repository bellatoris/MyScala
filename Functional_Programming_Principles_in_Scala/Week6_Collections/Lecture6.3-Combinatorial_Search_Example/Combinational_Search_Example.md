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
	s map(_ + 2)							// Set(3, 4, 5, 6, 7, 8)
	fruit filter (_.startsWith == "app")	// Set("apple")
	s.nonEmpty
	```
	
	* (see `Iterables` Scaladoc for a list of all supported operations)
	
## Sets vs Sequences
* The principal differences between sets and sequences are: 
	1. Sets are unordered; the elements of a set do not have a predefined order in which they appear in the set
	2. sets do not have duplicate elements:

		```scala
		s map (_ / 2) 		// Set(2, 0, 3, 1)
		```
			
	3. The fundamental operation on sets is `contains`:
	
		```scala
		s contains 5 		// true
		```
		
## Example: N-Queens
* The eight queens problem is to place eight queens on a chessboard so that no queen is threatened by another.
	* In other words, there can't be two queens in the same row, column, or diagonal.

* We now develop a solution for a chessboard of any size, not just 8.
* One way to solve the problem is to place a queen on each row. 
* Once we have place `k - 1` queens, one must place `kth` queen in a column where it's not "in check" with any other queen on the board.

## Algorithm
* We can solve this problem with a recursive algorithm:
	* Suppose that we have already generated all the solutions consisting of placing `k - 1` queens on a board of size `n`.
	* Each solution is represented by a list (of length `k - 1`) containing the numbers of columns (between `0` and `n - 1`).
	* The column number of the queen in the `k - 1th` row comes first in the list, followed by the column number of the queen in row `k - 2`, etc.
	* The solution set is thus representes as a set of lists, with one element for each solution.
	* Now, to place the `kth` queen, we generate all possible extensions of each solution preceded by a new queen. 
		
## Exercise
* Write a functions

	```scala
	def isSafe(col: Int, queens: List[Int]): Boolean = {
		val row = queens.length
		val queensWithRow = (row - 1 to 0 by -1) zip queens 
		queensWithRow forall {
			case (r, c) => col != c && math.abs(col - c) != row - r
		}
	}
	
	def show(queens: List[Int]) = {
		val lines = 
			for (col <- queens.reverse)
			yield Vector.fill(queens.length)("* ").updated(col, "X ").mkString
		"\n" + (lines mkString "\n")
	}
	```

* which tests if a queen placed in an indicated column `col` is secure amongst the other places queens.
* It is assumed that the new queen is placed in the next available row after the other placed queens (in other words: in row `queens.length`)