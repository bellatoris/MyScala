/**
  * Created by DoogieMin on 2016. 9. 18..
  */
abstract class IntSet {
  def incl(x: Int): IntSet
  def contains(x: Int): Boolean
  def union(other: IntSet): IntSet
}
