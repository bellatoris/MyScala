val tolerance = 0.0001

def isCloseEnough(x: Double, y: Double): Boolean = 
  ((x - y) / x).abs < tolerance

def fixedPoint(f: Double => Double)(firstGuess: Double): Double = {
  def iterate(guess: Double): Double = {
    val next = f(guess)
    println(next)
    if (isCloseEnough(guess, next)) next
    else iterate(next)
    }
  iterate(firstGuess)
}

def averageDamp(f: Double => Double)(x: Double) = (x + f(x)) / 2

//def sqrt(x: Double) = fixedPoint(y => (y + x / y) / 2)(1.0)
def sqrt(x: Double) = fixedPoint(averageDamp(y => x / y))(1.0)

sqrt(2)
