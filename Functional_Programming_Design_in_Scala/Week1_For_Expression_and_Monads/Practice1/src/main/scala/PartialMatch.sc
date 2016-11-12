object test {
  println("Welcome to the Scala WorkSheet")

  val f: String => String = { case "ping" => "pong" }
  f("ping")
  //f("abc")

  val g: PartialFunction[String, String] = { case "ping" => "pong" }
  g("ping")
  g.isDefinedAt("abc")

  val h: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: y :: rest => "two"
  }

  h.isDefinedAt(List(1, 2, 3))

  val i: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: rest =>
      rest match {
        case Nil => "two"
      }
  }

  i.isDefinedAt(List(1, 2, 3))
}