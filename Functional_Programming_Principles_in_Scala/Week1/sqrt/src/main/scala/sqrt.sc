object session {
  1+3
  def sqrt(x:Double): Double = {
    def isGoodEnough( guess: Double): Boolean = {
      if ((guess * guess - x).abs / x < 0.001) true
      else false
    }

    def sqrtIterate(guess: Double): Double = {
      if (isGoodEnough(guess)) guess
      else sqrtIterate((guess + x / guess) / 2)
    }

    sqrtIterate(x/2)
  }

  sqrt(1e60)

  def factorialTailRecursion(x: Int):Int = {
    def reverseFactorial(i: Int, result: Int):Int = {
      if (i == x) result * i
      else reverseFactorial(i + 1, result * i)
    }
    reverseFactorial(1, 1)
  }

  def factorial(n: Int): Int = {
    def loop(acc: Int, n: Int): Int = {
      if(n == 0) acc
      else loop(acc * n, n - 1)
    }
    loop(1, n)
  }
  factorial(4)
  factorialTailRecursion(5)
}