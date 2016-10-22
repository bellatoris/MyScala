object listfun {
  val nums = List(1, 2, 3, 4, 5)
  val fruits = List("apple", "pineapple", "pears", "orange")

  nums filter (x => x % 2 == 0)
  nums filterNot (x => x % 2 == 0)
  nums partition (x => x % 2 == 0)

  nums takeWhile (x => x - 1 == 0)
  nums dropWhile (x => x - 1 == 0)
  nums span (x => x - 1 == 0)

  def pack[T](xs: List[T]): List[List[T]] = xs match {
    case Nil => Nil
    case x :: xs1 =>
      val (first, rest) = xs span (y => y == x)
      first :: pack(rest)
  }
  pack(List("a", "a", "a", "b", "c", "c", "a")).head

  def encode[T](xs: List[T]): List[(T, Int)] = {
    pack(xs) map (ys => (ys.head, ys.length))
  }
  encode(List("a", "a", "a", "b", "c", "c", "a"))

  def concat[T](xs: List[T], ys: List[T]): List[T] =
    (xs foldRight ys) (_ :: _)

  def mapFun[T, U](xs: List[T], f: T => U): List[U] =
    (xs foldRight List[U]())(f(_) :: _)
  mapFun[Int, Int](nums, x => x * 2)

  def lengthFun[T](xs: List[T]): Int =
    (xs foldRight 0)((x, y) => 1 + y)

  lengthFun(fruits)
}