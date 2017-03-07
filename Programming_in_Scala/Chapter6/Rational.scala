class Rational(numer: Int, denom: Int) {

  require(denom != 0)

  private val g = gcd(numer.abs, denom.abs)

  val n = if (denom < 0) -numer / g else numer / g
  val d = if (denom < 0) -denom / g else denom / g

  def this(n: Int) = this(n, 1)

  override def toString = n + "/"+ d

  def add(that: Rational): Rational = 
    new Rational(n * that.d + that.n * d, d * that.d)
 
  def <(that: Rational): Boolean = 
    n * that.d < that.n * d

  def max(that: Rational): Rational = 
    if (this < that) that
    else this

  def +(that: Int): Rational = 
    this.+(new Rational(that))

  def +(that: Rational): Rational = 
    new Rational (
      n * that.d + that.n * d,
      d * that.d
    )

  def *(that: Int): Rational = 
    this.*(new Rational(that))
  def *(that: Rational): Rational = 
    new Rational(n * that.n, d * that.d)

  def unary_-(): Rational = 
    new Rational(-n, d)

  def -(that: Rational): Rational = 
    this.+(-that)
  def -(that: Int): Rational =
    this.-(new Rational(that))

  def /(that: Rational): Rational = 
    this.*(new Rational(that.d, that.n))
  def /(that: Int): Rational = 
    this./(new Rational(that))
 
                 
  private def gcd(a: Int, b: Int): Int = 
    if (b == 0) a else gcd(b, a % b)

}

object Main {
  def main(args: Array[String]) = {
    val y = new Rational(3)
    println(y)
  }
}
