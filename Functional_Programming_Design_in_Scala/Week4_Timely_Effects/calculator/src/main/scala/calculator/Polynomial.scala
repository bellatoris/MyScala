package calculator
import math.sqrt

object Polynomial {
  def computeDelta(a: Signal[Double], b: Signal[Double],
      c: Signal[Double]): Signal[Double] = {
    Signal(b()*b() - 4*a()*c())
  }

  def computeSolutions(a: Signal[Double], b: Signal[Double],
      c: Signal[Double], delta: Signal[Double]): Signal[Set[Double]] = {
    def nested(delta: Double, a: Double, b: Double): Set[Double] = {
      if (delta < 0) Set.empty
      else if (delta == 0) Set(-1 * b / (2 * a))
      else Set((-1 * b + sqrt(delta)) / (2 * a), (-1 * b - sqrt(delta)) / (2 * a))
    }
    Signal(nested(computeDelta(a, b, c)(), a(), b()))
  }
}
