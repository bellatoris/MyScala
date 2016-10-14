# Lecture 6.1 - Other Collections
## Other Sequences 
* We have seen that lists are *linear*: Access to the first element is much faster than access to the middle or end of a list.
* The Scala library also defines an alternative sequence implementation, `Vector`.
* This one has more evenly balanced access patterns than `List`.
* `Vector` is essentially represented as very shallow tree.
* 벡터는 자식이 32개인 트리이다. 마지막 인덱스를 접근하는게 매우 빠르다. 32개는 보통 현대 컴퓨터의 cache line이므로 한 벡터를 접근하는것은 매우 빠르다. 리스트는 리커시브하게 리니어해서 접근같은게 매우 느림, 아니 그렇게 벡터가 좋으면 왜 리스트를 남겨 놓은거야? 만약 너의 오퍼레이션이 head에 리커시브 하게 접근하는거면 리스트가 좀더 빠르니까, 또한 tail만 꺼내고 뭐 이런 오퍼레이션 같은 경웨 `List`는 constant time이지만 `Vector`는 복잡하다 그러니까 너의 operation이 recursive pattern이라면 `List`가 더 좋다. 그러나 bulk operation이라면 `Vector`가 낫지(`map`, `filter`).

## Operations on Vectors
* Vectors are created analogously to lists:

	```scala
	val nums = Vector(1, 2, 3, -88)
	val people = Vector("Bob", "James", "Peter")
	```

* They support the same orpations as lists, with the exception of `::`
* Instead of `x :: xs`, there is
	* `x +: xs` Create a new vector with leading element `x`, followed by all elements of `xs`.
	* `xs :+ x` Create a new vector with trailing element `x`, preceded by all elements of `xs`.

* (Note that the `:` always points to the sequence.)
* 벡터 또한 immutable하기 때문에 이미 존재하는 벡터를 바꿀 수 는 없다. 그래서 언제나 새로운 data structure를 만들어야 함. 예를 들어 이미 존재하는 벡터에 맨뒤에 새로운 값을 추가하고 싶다 하면 맨 마지막 어레이를 복사한후 거기에 값을 집어넣는다. 그 후에 root들을 새로 만들어서 새로 만든 벡터를 가리키게 한다. 
* Complexity: log_32(N) * 32(pointer 옮기는 연산)

## Collection Hierarchy
* A common base class of `List` and `Vector`is `Seq`, the clas fo all *sequences*.
* `Seq` itself is a subclass of `Iterable`.

## Arrays and Strings
* Arrays and Strings support the same operations as `Seq` and can implicitly be converted to sequences where needed.
* (They cannot be subclasses of `Seq` because they come from Java)

	```scala
	val xs: Array[Int] = Array(1, 2, 3)
	xs map (x => 2 * x)
	
	val ys: String = "Hello world!"
	ys filter (_.isUpper)
	```
	
## Ranges
* Another simple kind of sequence is the *range*.
* It represents a sequence of evenly spaced integers.
* Three operators:
	* `to` (inclusive), `until` (exclusive), `by` (to determine step value):

	```scala
	val r: Range = 1 until 5 	// 1, 2, 3, 4
	val s: Range = 1 to 5 		// 1, 2, 3, 4, 5
	1 to 10 by 3				// 1, 4, 7, 10
	6 to 1 by -2				// 6, 4, 2
	```
	
* Ranges a represented as single objects with three fields: lower boud, upper bound, step value.

## some more Sequence Operations:
* `xs exists p`: `true` if there is an element `x` of `xs` such that `p(x)` holds, `false` otherwise.
* `xs forall p`: `true` if `p(x)` holds for all elements `x` of `xs`, `false` otherwise.
* `xs zip ys`: A sequence of pairs drawn from corresponding elements of sequence `xs` and `ys`.
* `xs.unzip`: Splits a sequence or pairs `xs` into two sequences consisting of the first, repectively second halves of all pairs.
* `xs.flatMap f`: Applies collection-valued function `f` to all elements of `xs` and concatenates the results.
* `xs.sum`: The sum of all elements of this numeric collection.
* `xs.product`: The product of all elements of this numeric collection.
* `xs.max`: The maximum of all elements of this collection (an `Ordering` must exist)
* `xs.min`: The minimum of all elements of this collection