# Lecture 2.1 - Structural Induction on Trees

## Structural Indcution on Trees
Structural induction is not limited to lists; it applies to any tree structure. The general incution principle is the following:

To prove a property `P(t)` for all trees `t` of a certain type,

* show that `P(1)` holds for all leaves `1` of a tree,
* for each type of internal nore `t` with subtrees `s_1, ..., s_n` show that

	* `P(s_1) ^ ... ^ P(s_n)` *implies* `P(t)` 

## Example: IntSets
Recall our definition of `IntSet` with the operations `contains` and `incl`:

```scala
abstract class IntSet {
	def incl(x: Int): IntSet
	def contains(x: Int): Boolean
}

object Empty extends IntSet {
	def contains(x: Int): Boolean = false
	def incl(x: Int): IntSet = NonEmpty(x, Empty, Empty)
}

case class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
	def contains(x: Int): Boolean =
    	if (x < elem) left contains x
    	else if (x > elem) right contains x
   		else true
  
   def incl(x: Int): IntSet =
    	if (x < elem) NonEmpty(elem, left incl x, right)
    	else if (x > elem) NonEmpty(elem, left, right incl x)
    	else this
}
```

## The Laws of IntSet
What does it mean to prove the correctness of this implementation? One way to define and show the correctness of an implementation consists of proving the laws that it respects. In the case of `IntSet`, we have the following three laws:

For any set `s`, and elements `x` and `y`:

```scala
Empty contains x        = false
(s incl x) contains x   = true
(s incl x) contains y   = s contains y    if x != y
```
(In fact, we can show that these laws completely characterize the desired data type.)

## Proving the Laws of IntSet
How can we prove these laws?

### ***Proposition 1:*** `Empty contains x = false`.  
***Proof:*** According to the definition of `contains` in `Empty`.

### ***Propostion 2:*** `(s incl x) contains x = true`  
***Proof*** by structural induction on `s`.

**Base case:** `Empty`

```scala
(Empty incl x) contains x
= NonEmpty(x, Empty, Empty) contains x    // by definition of Emtpy.incl
= true                                    // by definition of NonEmpty.contains
```

**Induction step:** `NonEmpty(x, l, r)`

```scala
(NonEmpty(x, l, r) incl x) contains x
= NonEmpty(x, l, r) contains x            // by definition of NonEmpty.incl
= true                                    // by definition of NonEmpty.contains
```

**Induction step:** `NonEmpty(y, l, r)` **where** `y < x`

```scala 
(NonEmpty(y, l, r) incl x) contains x
= NonEmtpy (y, l, r incl x) contains x     // by definition of NonEmpty.incl
= (r incl x) contains x                    // by definition of NonEmpty.contains
= true                                     // by the induction hypothesis
```
**Induction step:** `NonEmpty(y, l, r)` **where** `y > x` is analogous

### ***Proposition 3:*** If `x != y` then `(xs incl y) contains x = xs contains x`.  
***Proof*** by structural induction on `s`. Assume that `y < x` (the dual case `x < y` is analogous).

**Base case**: `Empty`

```scala
(Empty incl y) contains x                 // to show: = Empty contains x
= NonEmpty(y, Empty, Empty) contains x    // by definition of Empty.incl
= Empty contains y                        // by definition of NonEmpty.contains
```

For the inductive step, we need to consider a tree `NonEmpty(z, l, r)`. We distinguish five cases:

1. `z = x`
2. `z = y`
3. `z < y < x`
4. `y < z < x`
5. `y < x < z`

#### First Two Cases: `z = x`, `z = y`
**Induction step:** `NonEmpty(x, l, r)`

```scala
(NonEmpty(x, l, r) incl y) contains x    // to show: = NonEmpty(x, l, r) contains x
= (NonEmpty(x, l incl y, r) contains x   // by definition of NonEmpty.incl
= true                                   // by definition of NonEmpty.contains
= NonEmpty(x, l, r) contains x           // by definition of NonEmpty.contains
```

**Induction step:** `NonEmpty(y, l, r)`

```scala
(NonEmpty(y, l, r) incl y) contains x    // to show: = NonEmpty(y, l, r) contains x
= (NonEmpty(y, l, r) incl y)             // by definition of NonEmpty.incl
```

#### Case `z < y`
**Induction step:** `NonEmpty(z, l, r)` **where** `z < y < x`

```scala
(NonEmpty(z, l, r) incl y) contains x    // to show: = NonEmpty(z, l, r) contains x
= NonEmpty(z, l, r incl y) contians x    // by definition of NonEmpty.incl
= (r incl y) contains x                  // by definition of NonEmpty.contains
= r contains x                           // by the induction hypothesis
= NonEmpty(z, l, r) contains x           // by definition of NonEmpty.contains
```

#### Case `y < z`
**Induction step:** `NonEmpty(z, l, r)` **where** `y < z < x`

```scala
(NonEmpty(z, l, r) incl y) contains x    // to show: = NonEmpty(z, l, r) contains x
= NonEmpty(z, l incl y, r) contains x    // by definition of NonEmpty.incl
= r contains x                           // by definition of NonEmpty.contains
= NonEmpty(z, l, r) contains x           // by definition of NonEmpty.contains
```

#### Case `x < z`
**Induction step:** `NonEmpty(z, l, r)` **where** `y < x < z`

```scala
(NonEmpty(z, l, r) incl y) contains x    // to show: = NonEmpty(z, l, r) contains x
= NonEmpty(z, l incl y, r) contains x    // by definition of NonEmpty.incl
= (l incl y) contains x                  // by definition of NonEmpty.contains
= l contains x                           // by the induction of hypothesis
= NonEmpty(z, l, r) contains x           // by definition of NonEmpty.contains
```
These are all the cases, so the proposition is established. 

> Odersky: "I would argue that the complexity of the purely functional equational proofs often compared favorably, with what you would have to do in an imperative language."

## Exercise (Hard)
Suppose we add a function `union` to `IntSet`:

```scala
abstract class IntSet { ...
	def union(other: IntSet): IntSet
}

object Empty extends IntSet { ...
	def union(other: IntSet) = other
}

class NonEmtpy(x: Int, l: IntSet, r: IntSet) extends IntSet { ...
	def union(other: IntSet): IntSet = (l union (r union other)) incl x
}
```

The correctness of `union` can be translated into the following law:  
***Proposition 4:***

```scala
(xs union ys) contains x = xs contains x || ys contains x 
```
Show **proposition 4** by using structural induction on `xs`.

**Base case**: `Empty`

```scala
(Empty union ys) contains x            // to show: = Empty contains x || ys contains x
= ys contains x                        // by definition of Empty.union
= Empty contains x || ys contains x    // by definition of Empty.contains
```

For the inductive step, we need to consider a tree `NonEmpty(z, l, r)`. We distinguish two cases:

1. `z == x`
2. `z != x`

#### Case `z == x`
**Induction step:** `NonEmpty(z, l, r)` **where** `z == x`

```scala
(NonEmpty(z, l, r) union ys) contains x         // to show: NonEmpty(z, l, r) contains x || ys contains x
= ((l union (r union ys)) incl z) contains x    // by definition of NonEmpty.union
= ((l union (r union ys)) incl x) contains x    // by z == x
= true                                          // by proposition 2
= true || ys contains x
= NonEmpty(z, l, r) contains x || ys contains x // by definition of NonEmtpy.contains
```

#### Case `z != x`
**Induction step:** `NonEmtpy(z, l, r)` where `z != x`

```scala
(NonEmpty(z, l, r) union ys) contains x         // to show: NonEmpty(z, l, r) contains x || ys contains x 
= ((l union (r union ys) incl z) contains x     // by definition of NonEmpty.union
= (l union (r union ys)) contains x             // by proposition 3
= l contains x || (r union ys) contains x       // by the induction of hypothesis
= l contains x || r contains x || ys contains x // by the induction of hypothesis
= xs contains x || ys contains x                // by definition of NonEmpty.contains
```