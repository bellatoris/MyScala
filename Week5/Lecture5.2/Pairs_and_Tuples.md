# Lecture 5.2 - Pairs and Tuples

## Sorting Lists Faster
* As a non-tirival example, let's design a function to sort lists that is more efficient than insertion sort.
* A good algorithm for this is *merge sort*. The idea is as follows:
* If the list consists of zero or one elements, it is already sorted.
* Otherwise,
	* Separate the list into two sub-lists, each containing around half of the elements of the original list.
	* Sort the two sub-lists.
	* Merge the two sorted sub-lists into a single sorted list. 