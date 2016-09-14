

class Rational(x: Int, y: Int) {
  require(y > 0, "denominator must be positive")
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)
  private val g = gcd(x, y)
  val numer = x
  val denom = y

  def <(that: Rational) =
    numer * that.denom < that.numer * denom

  def max(that: Rational) =
    if (this < that) that
    else this

  def add(that: Rational) =
    new Rational(
      numer * that.denom + that.numer * denom,
      denom * that.denom)

  override def toString = numer/g + "/" + denom/g

  def neg = new Rational(-numer, denom)
  def sub(that: Rational) = add(that.neg)
}

val x = new Rational(1, 3)
x.numer
x.denom

val y = new Rational(5, 7)

val z = new Rational(3, 2)

x.sub(y).sub(z)

y.add(y)
x < y
x.max(y)

y add y




