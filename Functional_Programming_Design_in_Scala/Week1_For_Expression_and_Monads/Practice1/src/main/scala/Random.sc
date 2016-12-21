import java.util.Random
object test {
  val rand = new Random()

  trait Generator[+T] {
    self => 		// an alias for "this".

    def generate: T

    def map[S](f: T => S): Generator[S] = new Generator[S] {
      def generate = f(self.generate)
    }

    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
      def generate = f(self.generate).generate
    }
  }

  val integers = new Generator[Int] {
    val rand = new java.util.Random
    def generate = rand.nextInt()
  }

//  val booleans = new Generator[Boolean] {
//    def generate = integers.generate > 0
//  }
//
//  val pairs = new Generator[(Int, Int)] {
//    def generate = (integers.generate, integers.generate)
//  }

  val booleans  = for (x <- integers) yield x > 0

  def pairs[T, U](t: Generator[T], u: Generator[U]) = for {
    x <- t
    y <- u
  } yield (x, y)
}