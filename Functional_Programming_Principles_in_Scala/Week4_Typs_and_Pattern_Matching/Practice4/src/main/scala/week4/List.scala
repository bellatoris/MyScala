/**
  * Created by DoogieMin on 2016. 9. 26..
  */
package week4

trait List[+T] {
  def isEmpty: Boolean
  def head: T
  def tail: List[T]
  def prepend[U >: T](elem: U): List[U] = new Cons(elem, this)
}

class Cons[T](val head: T, val tail: List[T]) extends List[T] {
  def isEmpty = false
}

class Nil extends List[Nothing] {
  def isEmpty: Boolean = true
  def head: Nothing = throw new NoSuchElementException("Nil.head")
  def tail: Nothing = throw new NoSuchElementException("Nil.tail")
}

/**
  * Because Nil is a List of Nothing.
  * Nothing is a subtype of String,
  * and Lists are covariants.
  * So that's why List of Nothing is subtype of
  * List of String.
  */
object test {
  val x: List[String] = new Nil
}