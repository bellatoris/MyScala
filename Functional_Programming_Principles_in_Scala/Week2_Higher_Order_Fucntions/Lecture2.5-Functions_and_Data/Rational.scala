class Rational(n: Int, d: Int) {
  
  private def gcd(a: Int, b: Int): Int =
    if (b == 0) a else gcd(b, a % b)

  private val g = gcd(n.abs, d.abs)

  val numer = n / g
  val denom = d / g

  override def toString = numer + "/" + denom
}

object Rational {
  def apply(n: Int, d: Int) = 
    new Rational(n, d)

  def +(r1: Rational, r2: Rational) = {
    new Rational(r1.numer * r2.denom + r2.numer * r1.denom, r1.denom * r2.denom)
  }
}

