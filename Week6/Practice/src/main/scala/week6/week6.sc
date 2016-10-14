object test {
  val xs = Array(1, 2, 3, 44)
  val ys: String = "Hello world!"
  ys filter (c => c.isUpper)
  ys exists (c => c.isUpper)
  ys forall (c => c.isUpper)

  val pairs = List(1,2,3) zip ys
  val (a, b) = pairs.unzip

  ys flatMap (c => List('.', c))
  xs.min
  xs.max
  xs.sum
}