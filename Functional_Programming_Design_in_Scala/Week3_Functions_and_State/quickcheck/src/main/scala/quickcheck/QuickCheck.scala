package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    i <- arbitrary[Int]
    h <- oneOf(const(empty), genHeap)
  } yield insert(i, h)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { a: Int =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("min2") = forAll { (a1: Int, a2: Int) =>
    val h = insert(a2, insert(a1, empty))
    findMin(h) == (if (a1 < a2) a1 else a2)
  }

  property("empty") = forAll{ a: Int =>
    val h = deleteMin(insert(a, empty))
    isEmpty(h)
  }

  property("heapSort") = forAll { l: List[Int] =>
    def listToHeap(list: List[Int], heap: H): H = {
      if (list.isEmpty) heap
      else listToHeap(list.tail, insert(list.head, heap))
    }

    def heapSort(list: List[Int], heap: H): Boolean = {
      if (list.isEmpty) true
      else if (list.head == findMin(heap)) heapSort(list.tail, deleteMin(heap))
      else false
    }

    val heap = listToHeap(l, empty)
    val sortedList = l.sorted

    heapSort(sortedList, heap)
  }

  property("meld") = forAll { (h1: H, h2: H) =>
    val min = if (findMin(h1) < findMin(h2)) findMin(h1) else findMin(h2)
    val melded = meld(h1, h2)
    findMin(melded) == min
  }

}
