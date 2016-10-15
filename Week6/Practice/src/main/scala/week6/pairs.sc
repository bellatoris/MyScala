object pairs {

  def isPrime(n: Int): Boolean = (2 until n) forall (n % _ != 0)
  val n = 7
  (1 until n) flatMap (i =>
    (1 until i) map (j => (i, j))) filter (
    pair => isPrime(pair._1 + pair._2))

  for {
    i <- 1 until n
    j <- 1 until i
    if isPrime(i + j)
  } yield (i, j)

  def scalarProduct(xs: List[Double], ys: List[Double]): Double =
    (for {
      (i, j) <- xs zip ys
    } yield i * j).sum

  scalarProduct(List(1, 3, 4), List(2, 4, 5))
}