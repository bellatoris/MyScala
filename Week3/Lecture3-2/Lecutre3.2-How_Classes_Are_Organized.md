# Lecutre 3.2 - How Classes Are Organized
## Packages
* Classes and objects are organized in packages.
* To place a class or object inside a package, use a package clause at the top of your source file.

	```scala
	package progfun.examples
	object Hello { ... }
	```
* This would place `Hello` in the package `progfun.examples`.
* You can then refer to `Hello` by its *fully qualified name* `progfun.example.Hello`. For instacne, to run `Hello` program:
	
	```scala
	> scala progfun.examples.Hello
	```

## Forms of Imports
* Imports come in several forms:

	```scala
	import week3.Rational				// imports just Rational
	import week3.{Rational, Hello}	// imports both Rational and Hello
	import week3._						// imports everything in package week3
	```
* The first two forms are called *named imports*.
* The last form is called a *wildcard import*.
* You can import from either a package or an object

## Automatic Imports
* Some entities are automatically imported in any Scala profram.
* These are:
	*  All members of packages `scala`
	*  All members of packages `java.lang`
	*  All members of the singleton object `scala.Predef`.

* Here are fully qualified names of some types and functions which you have seen so far:
	
	```
	Int 			scala.Int
	Boolean		scala.Boolean
	Object			java.lang.Object
	require		scala.Predef.require
	assert			scala.Predef.assert
	```
	
## Traits
* In Java, as well as in Scala, a class can only have one superclass. (**Single inheritance language**)
* But what if a calss has several ntural supertypes to which it conforms or from which it wants to inherit code?
* Here, you could use `traits`.
* A trait is declared like an abstract class, just with `trait` instead of `abstract class`.

```scala
trait Planar {
	def height: Int
	def width: Int
	def surface: height * width
```
* Classes, objects and traits can inherit from at most one class but arbitrary many traits.
* Example:
	* `class Square extends Shape with Planar with Movable ...`
* Traits resemble interfaces in Java, but are more powerful because they can contains fields and concrete methods.
* On the other hand, traits cannot have (value) parameters, only classes can. (What meaming?, Ah hah I got it.)

## Top Types
* At any top of the type hierarchy we find:
	* `Any`		
		* the base types of all types
		* Methods: `'=='. '!=', 'equals', 'hashcode', 'toString'`
	* `AnyRef`
		* The base type of all reference types;
		* Alias of `'java.lang.Object'`
	* `AnyVal`
		* The base types of all primitive types.

## The Nothing Type
* `Nothing` is at the bottom of Scala's type hierarhy. It is a subtype of every other type.
* There is no value of type `Nothing`
* Why is that useful?
	* To signal abnormal termination
	* As an element type of empty collections (see next session)

## Exceptions
* Scala's exception handling is similar to Java's.
* The expression
	* `throw Exc`
* aborts evaluationwith the exception `Exc`.
* The type of this expression is `Nothing`. 

## The Null Type
* Every reference class type also has `null` as a value
* The type of `null` is `Null`.
* `Null` is a subtype of every class that inherits from `Object`; it is incompatible with subtypes of `AnyVal`.
	
	```scala
	val x = null 				// x: Null
	val y: String = null	// y: String
	val z: Int = null 		// error: type mismatch
	```
* Example:
	* What type of return `if (true) 1 else false`
	* `AnyVal`	 
