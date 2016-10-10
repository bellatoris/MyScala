object week5 {
  val fruit = List("apples", "oranges", "pears")
  val nums = List(1, 2, 3)
  val diag3 = List(List(1, 0, 0), List(0, 1, 0), List(0, 0, 1))
  val empty = List()

  def removeAt(n: Int, xs: List[Int]) = (xs take n) ::: (xs drop n + 1)
  def flatten(xs: List[Any]): List[Any] = xs match {
    case List() => List()
    case y :: ys => y match {
      case z :: zs => flatten(z :: zs) ::: flatten(ys)
      case z => z :: flatten(ys)
    }
  }
  def msort(xs: List[Int]): List[Int] = {
    val n = xs.length / 2
    if (n == 0) xs
    else {
      def merge(xs: List[Int], ys: List[Int]): List[Int] = {
        if (xs.isEmpty) ys
        else if(ys.isEmpty) xs
        else if (xs.head < ys.head) xs.head :: merge(xs.tail, ys)
        else ys.head :: merge(xs, ys.tail)
      }
      val (fst, snd) = xs splitAt n
      merge(msort(fst), msort(snd))
    }
  }
}

week5.removeAt(1, week5.nums)
week5.flatten(List(List(1, 1), 2, List(3, List(5, 8))))
week5.flatten(List(List(List(3, List(5, 8)))))

week5.msort(List(3,4,5,4,23,24,23,453,1,2,36,23,42,34,23,5,235))

