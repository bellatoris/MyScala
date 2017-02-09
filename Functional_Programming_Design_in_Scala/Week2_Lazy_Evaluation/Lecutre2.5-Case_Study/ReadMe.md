# Lecture 2.5 - Case Study: The Water Pouring Problem

## The Water Pouring Problem
You are given a faucet and a sink and a number of glasses with different sizes. So, just for the sake of the example, let's assume there are glass which has size four deciliters, and another glass which has size nine deciliters. What you need to do is put use of a given quantity, for the sake of the argument, six deciliters of liquid in one of the glasses. You can either fill a glass completely using the faucet or you can empty it in the sink or you can pour from one glass to the other, until either the glass from which you pour is empty, or the glass into which you pour is full.

## States and Moves
* Glass: `Int`
* State: `Vector[Int]` (one entry per glass)
* Moves:
	* `Empty(glass)`
	* `Fill(glass)`
	* `Pour(from, to)`

## Variants
In a program of the complexity of the pouring program, there are many choices to be made. Choice of representations.

* Specific classes for moves and paths, or some encoding?
* Ojbect-oriented methods, or naked data structures with functions?

The present elaboration is just one solution, and not necessarily the shortest one.

## Guiding Principles for Good Design
* Name everything you can.
* Put operations into natural scopes.
* Keep degrees of freedom for future refinements.