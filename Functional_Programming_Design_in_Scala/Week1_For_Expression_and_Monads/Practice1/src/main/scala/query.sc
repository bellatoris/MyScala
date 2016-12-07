object test {

  case class Books(title: String, authors: List[String])

  val books = Set(
    Books(title = "Structure and Interpretation of Computer Programs",
      authors = List("Abelson, Harald", "Sussman, Gerald J.")),
    Books(title = "Introduction to Functional Programming",
      authors = List("Bird, Richard", "Wadler, Phil")),
    Books(title = "Effective Java",
      authors = List("Bloch, Joshua")),
    Books(title = "Effective Java2",
      authors = List("Bloch, Joshua")),
    Books(title = "Java Puzzlers",
      authors = List("Bloch, Joshua", "Gafter, Neal")),
    Books(title = "Programming in Scala",
      authors = List("Odersky, Martin", "Spoon, Lex", "Venners, Bill")))

  for (b <- books; a <- b.authors if a startsWith "Bird,")
    yield b.title

  for (b <- books if (b.title indexOf "Program") >= 0)
    yield (b.title, b.title indexOf "Program")

  for {
    b1 <- books
    b2 <- books
    if b1.title != b2.title
    a1 <- b1.authors
    a2 <- b2.authors
    if a1 == a2
  } yield a1

  for {
    b1 <- books
    b2 <- books
    if b1.title < b2.title
    a1 <- b1.authors
    a2 <- b2.authors
    if a1 == a2
  } yield a1


  for {
    b1 <- books
    b2 <- books
    if b1.title < b2.title
    a1 <- b1.authors
    a2 <- b2.authors
    if a1 == a2
  } yield a1

  for (b <- books; a <- b.authors if a startsWith "Bird")
    yield b.title

  books.flatMap(b =>
    b.authors.withFilter(a => a startsWith "Bird").map(y => b.title))
}