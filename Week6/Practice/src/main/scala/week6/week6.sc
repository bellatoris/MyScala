object test {
  val xs = Array(1, 2, 3, 44)
  val ys: String = "Hello world!"
  ys filter (c => c.isUpper)
  ys exists (c => c.isUpper)
  ys forall (c => c.isUpper)

  val
}