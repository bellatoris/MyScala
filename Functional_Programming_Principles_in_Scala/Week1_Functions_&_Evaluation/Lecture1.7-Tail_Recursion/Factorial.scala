import scala.annotation.tailrec

def factorial(n: Int): Int = {
  @tailrec
  def nested(n: Int, fac: Int): Int = {
    if (n == 0) fac 
    else nested(n - 1, fac * n)
  }
  nested(n, 1)
}

println(factorial(5))
