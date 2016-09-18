import week3._

Hello.hi

def error(msg: String) = throw new Error(msg)

abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(s: Int): Boolean
  def union(other: IntSet): IntSet
}

class Empty extends IntSet {
  def contains(x: Int): Boolean = false
  def incl(x: Int): IntSet = new NonEmpty(x, new Empty, new Empty)
  def union(other: IntSet): IntSet = other
  override def toString = "."
}

class NonEmpty(elem: Int, left: IntSet, right: IntSet) extends IntSet {
  def contains(x: Int): Boolean =
    if (x < elem) left contains x
    else if (x > elem) right contains x
    else true

  def incl(x: Int): IntSet =
    if (x < elem) new NonEmpty(elem, left incl x, right)
    else if (x > elem) new NonEmpty(elem, left, right incl x)
    else this

  def union(other: IntSet): IntSet = {
    ((left union right) union other) incl elem
  }
  override def toString = "{" + left + elem + right + "}"
}

val t1 = new NonEmpty(3, new Empty, new Empty)
val t2 = t1 incl 4

def singleton[T](elem: T) = new week3.Cons[T](elem, new week3.Nil[T])

singleton(1)
