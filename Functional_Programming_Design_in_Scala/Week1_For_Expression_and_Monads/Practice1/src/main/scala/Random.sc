import java.util.Random
object test {
  val rand = new Random()

  trait Generator[+T] {
    self => 		// an alias for "this".

    def generate: T

    def map[S](f: T => S): Generator[S] = new Generator[S] {
      def generate: S = f(self.generate)
    }

    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
      def generate: S = f(self.generate).generate
    }
  }

  val integers = new Generator[Int] {
    val rand = new java.util.Random
    def generate: Int = rand.nextInt()
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

  def pairs2[T, U](t: Generator[T], u: Generator[U]) =
    t flatMap (x => u map (y => (x, y)))

  def pairs3[T, U](t: Generator[T], u: Generator[U]) = new Generator[(T, U)] {
    def generate =  (t.generate, u.generate)}

  integers.generate
  pairs(integers, for (x <- integers) yield x > 0).generate

  pairs3(integers, booleans).generate
  pairs3(integers, booleans).generate

  def single[T](x: T): Generator[T] = new Generator[T] {
    def generate = x
  }

  def choose(lo: Int, hi: Int) : Generator[Int] =
    for (x <- integers) yield lo + x % (hi - lo)

  def oneOf[T](xs: T*): Generator[T] =
    for (idx <- choose(0, xs.length)) yield xs(idx)

  val a = integers.generate
  trait Tree

  case class Inner(left: Tree, right: Tree) extends Tree

  case class Leaf(x: Int) extends Tree

  def lists: Generator[List[Int]] = for {
    isEmpty <- booleans
    list <- if (isEmpty) emptyLists else nonEmptyLists
  } yield list

  def emptyLists = single(Nil)

  // recursion
  def nonEmptyLists = for {
    head <- integers
    tail <- lists
  } yield head :: tail

  def trees: Generator[Tree] = for {
    isLeaf <- booleans
    tree <- if (isLeaf) leafs else inners
  } yield tree

  def leafs: Generator[Leaf] = for {
    x <- integers
  } yield Leaf(x)

  def inners: Generator[Inner] = for {
    left <- trees
    right <- trees
  } yield Inner(left, right)

  trees.generate

  def test[T](g: Generator[T], numTimes: Int = 100)(nestedTest: T => Boolean): Unit = {
    for (i <- 0 until numTimes) {
      val value = g.generate
        assert(nestedTest(value), "test failed for " + value)
    }
    println("passed " +numTimes+" test")
  }

  val b = pairs(lists, lists)
  b.generate

  test(b) {
    case (xs, ys) => (xs ++ ys).length > xs.length
  }
}