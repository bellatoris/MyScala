# Lecture 1.1 - Queries with For

## Queries with `for`
* The `for` notation is essentially equivalent to the common operations of query languages for databases. Languages such as SQL and XQuery.
* **Examples:** Suppose that we have a database `books`, represented as a list of books.

	```scala
	case class Books(title: String, authors: List[String])
	
	val books: List[Books] = List(	Books(title = "Structure and Interpretation of Computer Programs",
		 authors = List("Abelson, Harald", "Sussman, Gerald J.")),	Books(title = "Introduction to Functional Programming",     	 authors = List("Bird, Richard", "Wadler, Phil")),	Books(title = "Effective Java",    	 authors = List("Bloch, Joshua")),	Books(title = "Java Puzzlers",    	 authors = List("Bloch, Joshua", "Gafter, Neal")),	Books(title = "Programming in Scala",   		 authors = List("Odersky, Martin", "Spoon, Lex", "Venners, Bill")))	```
	
## Some Queires
* To find the titles of books whose author's name is "Bird":

	```scala
	for (b <- books; a <- b.authors if a startsWith "Bird,")
	yield b.title
	```
	
* To find all the books which have the word "Program" in the title:

	```scala
	for (b <- books if (b.title indexOf "Program") >= 0)
	yield b.title
	```
	`indexOf` produces the index of the substring it it appears or -1 ir it doesn't.
	
## Another Query
* To find the names of all authors who have written at least two books present in the database.

	```scala
	for {
		b1 <- books
		b2 <- books
		if b1 != b2
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1
	```
	Why do solutions show up twice?  
	
	* Suppose there are books b1 and b2 written by the same author. (b1, b2) and (b2, b1) are regarded as different pairs. So the author shows twice.
	  
	How can we avoid this?  
	
	* Just take unique pair.
	
## Modified Query
* To find the names of all authors who have written at least two books present in the database.

	```scala
	for {
		b1 <- books
		b2 <- books
		if b1.title < b2.title
		a1 <- b1.authors
		a2 <- b2.authors
		if a1 == a2
	} yield a1
	```
	If the title of the first book must lexicographically smaller than the title of the second book, we can take only unique pair of books.
	
## Problem
* What happens if an author has published three books?
	* The author is printed three times. Suppose there are books b1, b2, b3 written by the same author. There are three pairs of (b1, b2), (b2, b3), and (b1, b3). Therefore, even if titles are sorted in alphabetical order, author names appear three times.

* *Solution:* We must remove duplicate authors who are in the results list twice.
* This is achieved using the `distinct` method on sequences:

	```scala 
	{ for {
			b1 <- books
			b2 <- books
			if b1.title < b2.title
			a1 <- b1.authors
			a2 <- b2.authors
			if a1 == a2
		} yield a1
	}.distinct
	```
* Perhaps the data structure choosen at first seems to be inadequate for the problem. If you use `Set` instead of `List`, you can solve the duplicate problem much more easily.
	
	