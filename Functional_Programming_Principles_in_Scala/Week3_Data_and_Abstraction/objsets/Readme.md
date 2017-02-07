# Programming Assignment: Object-Oriented Sets

* Complete the `TwwetSet.scala` file.
	* This file defines an abstract class `TweetSet` with two concrete subclass
		* `Empty` which represent an empty set.
		* `NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet)`, which represents a non-empty sets as a binary tree rooted at elem.
		
	* The tweets are indexed by their text bodies: 
		* the bodies of all tweets on the left are lexicographically smaller than `elem` 
		* the bodies of all tweets on the right are lexicographically greater than `elem`.

	* **These class are immutable**: the set-theoretic operations do not modify this but should return a new set.

## 1. Filtering
* Implement filtering on tweet sets.
	* Complete stubs for the methods `filter` and `filterAcc`, `filter` takes an argument a function, the predicate, which takes a `tweet` and returns a `boolean`.
	* `filter` then returns the subset of all the `tweets` in the original set for which the predicate is true.

> Hint: start by defining the helper method `filterAcc` which takes an accumulator contains the ongoing result of the filtering.

```scala 
/** This method takes a predicate and returns a subset of all the elements
 * in the original set for which the predicate is true.
 */
 def filter(p: Tweet => Boolean): TweetSet
 def filterAcc(p: Tweet=> Boolean, acc: TweetSet): TweetSet
```
> The definition of `filter` in terms of `filterAcc` should then be straightforward.

## 2. Taking Unions
* Implement `union` on tweet sets.
	* Complete the stub for method `union`. 
	* The method union take another set that, and computes a *new* set which is the union of this and that 
	
	```scala
	def union(that: TweetSet): TweetSet
	```
* Note: which classes to define the `union` method (should it be abstract in class TweetSet?).
* `((left union right) union other) incl elem` 
	* using the substitution model we can see that once the left and right branch trees are combined (inner union), for executing the outer union on the resulting tree it goes again all the way through it recursively. 
	* This means the tree gets traversed as many times as nodes has the tree, and for every level of the tree the process is repeated for every underlying tree.
	* **exponential complexity!**
* `left union (right union (other incl elem))` 
	* **traverse tree only once**

We could instead traverse one of the trees only once, and include each of the nodes from one tree on the other.



## 3. Sorting Tweets by Their influence
* The more often a tweet is "re-tweeted", the more influential it is.
* Implement `descendingByRetweet` to `TweetSet` which should produce a linear sequence of tweets (as an instance of class `TweetList`), ordered by their number of retweets:
	
```scala
def descendingByRetweet: TweetList
```

* This method reflects a common pattern when transforming data structures.
	* While traversing one data structure, we're building a second data structure. 
	* Idea: start with the empty list `Nil`, and to find the tweet with the most retweets in the input `TweetSet`.
	* This tweet is removed from the `TweetSet` 
		* We obtain a new TweetSet that has all the tweets of the original set except the tweet that was "removed";
		*  This *immutable* set operation, `remove`, is alreay implemented for you.
	* and added to the result list by creating a new `Cons`.
	* After that, the process repeats itself, but now we are searching through a `TweetSet` with one less tweet.

> Hint: start by implementing the method `mostRetweeted` which returns the most popular tweet of a `TweetSet`.

## 4. Tying everything together
* Detect influential tweets in a set of recent tweets.
	* `TweetReader.scala` containing several hundred tweets is provieded.
	* `TweetReader.allTweets` returns an instance of `TweetSet` containing a set of all available tweets.
* Given two lists of keywords.
	* The first list corresponds to keywords associated with Google and Android smartphones.
	* The second list corresponds to keywords associated with Apple and iOS devices.
* Detect which platform has generated more interest or activity in the past few days.
* As a first step, use the functionality you implemented in the first parts of this assignment to create two different `TweetSets`, `googleTweets`and `appleTweets`.
	* The first `TweetSet`, `googleTweets`, should contain all tweets that mention (in their "text") one of the keywords in the google list.
	* The second `TweetSet`, `appleTweets`, should contain all tweets that mention one of the keyword in the apple list.
* Their signature is as follows:

```scala
lazy val googleTweets: TweetSet
lazy val appleTweets: TweetSet
```

> Hint: use the exists method of the `List` and contains method of `classjava.lang.Sring`

* From the *union* of those two `TweetSets`, produce trending, an instance of class `TweetList` representing a sequence of tweets ordered by their number of tweets:

```scala
lazy val trending: TweetList
```