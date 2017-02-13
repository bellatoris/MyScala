# Lecture 3.2 - Identity and Change

## Identity and Change
Assignment poses the new problem of deciding whether two expressions are "the same". When one excludes assignments and one writes:

```scala
val x = E; val y = E
```
where `E` is an arbitrary expression, then it is reasonable to assume that `x` and `y` are the same. That is to say that we could have also written:

```scala
val x = E; val y = x
```
(This property is usually called ***referential transparency***)

But once we allow the assignment, the two formulations are differnet. For example:

```scala
val x = new BankAccount
val y = new BankAccount
```

**Question:** Are `x` and `y` the same?

* No
 
## Operational Equivalence
To respond to the last question, we must specify what is meant by "the same". The precise meaining of "being the same" defined by the property of ***operational equivalence.*** In a somewhat informal way, this property is stated as follows. Suppose we have two definitions `x` and `y`. `x` and `y` are operationally equivalent if ***no possible test*** can distinguish between them.

## Testing for Operational Equivalence
To test if `x` and `y` are the same, we must 

* Execute the defintions followed by an arbitrary sequence `f` of operations that involves `x` and `y`, observing the possible outcomes.

	```scala
	val x = new BankAccount
	val y = new BackAccount
	f(x, y)
	
	f(x, x)
	```
	
* Then, execute the definitions with another sequence `S'` obtained by renaming all occurrences of `y` by `x` in `S`
* If the results are different, then the expressions `x` and `y` are certainly different.
* On the other hand, if all posslible pairs of sequences `(S, S')` produce the same result, then `x` and `y` are the same.

## Counterexample of Operational Equivalence
Based on this definition, let's see if the expressions 

```scala
val x = new BankAccount
val y = new BankAccount
```
define values `x` and `y` that are the same. Let's follow the definitions by a test sequence:

```scala
val x = new BankAccount
val y = new BankAccount
x deposit 30               // val res1: Int = 30 
y withdraw 20              // java.lang.Error: insufficient funds
```

Now rename all occurrences of `y` with `x` in this sequence. We obtain:

```scala
val x = new BankAccount
val y = new BankAccount
x deposit 30               // val res1: Int = 30
x withdraw 20              // val res2: Int = 10
```
The final results are different. We conclude that `x` and `y` are not the same.

## Establishing Operational Equivalence
On the other hand, if we define 

```scala
val x = new BankAccount
val y = x
```
then no sequence of operations can distinguish between `x` and `y`, so `x` and `y` are the same in this case.

## Assignments and Substitution Model
The preceding examples show that our model of computation by substitution cannot be used. Indeed, according to this model, one can always replace the name of a value by the expression that defines it. For example, in

```scala
val x = new BankAccount 
val y = x
```
the `x` in the definition of `y` could be replcaed by `new BankAccount`. But we have seen that this change leads to a different program! The substitution model ceases to be valid when we add the assignment. It is possible to adapt the substitution model by introducing a ***store***, but this becomes considerably more complicated.