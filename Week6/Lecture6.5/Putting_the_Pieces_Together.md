# Lecture 6.5 - Putting the Pieces Together
## Task
* Phone keys have mnemonics assigned to them.

	```scala
	val mnemonics = Map(
		'2'	 -> "ABC", '3' -> "DEF", '4' -> "GHI", '5' -> "JKL",
		'6' -> "MNO", '7' -> "PQRS", '8' -> "TUV", '9' -> "WXYZ")
	```
	
* Assume you are given a dictionary words as a list of words.
* Design a method `translate` such that

	```scala
	translate(phoneNumber)
	```
	
	produces all phrases of words that can serve as mnemonics for the phone number.
	
* **Example:** The phone number "7225247386" should have the mnemonic `Scala is fun` as one element of the set of solution phrases.

## Backgorund
* This example was taken from:

> *Lutz Prechelt: An Empirical Comparison of Seven Programming Languages.*  
> *IEEE Computer 33(10): 23 - 29 (2000)*

* Tested with Tcl, Python, Perl, Rexx, Java, C++, C.
* Code size medians:
	* 100 loc for scripting languages
	* 200-300 loc for the others

## The Future?
* Scala's immutable collections are:
	* *easy to use*: few steps to do the job.
	* *concise*: one word replaces a whole loop.
	* *safe*: type checker is really good at catching errors.
	* *fast*: collection ops are tuned, can be parallelized.
	* *universal*: one vocabulary to work on all kinds of collections.

* This makes them a vary attractive tool for software development.


