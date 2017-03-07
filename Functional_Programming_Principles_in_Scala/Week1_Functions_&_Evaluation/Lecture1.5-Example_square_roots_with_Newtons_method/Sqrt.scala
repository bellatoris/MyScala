def sqrtIter(guess: Double, x: Double): Double =
  if (isGoodEnough(guess, x)) guess
  else sqrtIter(improve(guess, x), x)

def improve(guess: Double, x: Double) = 
  (guess + x / guess) / 2

def isGoodEnough(guess: Double, x: Double) = 
  (guess * guess - x).abs / x < 0.001

def sqrt(x: Double) = sqrtIter(1.0, x)

println(sqrt(0.1e-20))
println(sqrt(1.0e20))
println(sqrt(1.0e50))
