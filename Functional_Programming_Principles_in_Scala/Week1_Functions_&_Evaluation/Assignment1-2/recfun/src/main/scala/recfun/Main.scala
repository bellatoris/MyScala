package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if (c == 0 || c == r) 1
      else pascal(c - 1, r - 1) + pascal(c, r - 1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def countBracket(count: Int, chars: List[Char]): Int = {
        if (chars.isEmpty) count
        else if (chars.head.equals('(')) countBracket(count + 1, chars.tail)
        else if (chars.head.equals(')')) {
          if (count - 1 < 0) count - 1
          else countBracket(count - 1, chars.tail)
        }
        else countBracket(count, chars.tail)
      }
      val result = countBracket(0, chars)
      result == 0
    }

  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if (coins.isEmpty) 0
      else if (money == 0) 1
      else if (money < 0) 0
      else countChange(money - coins.head, coins) +
        countChange(money, coins.tail)
//      def countChangingPossible(change: Int, coins: List[Int]): Int = {
//        if (coins.isEmpty) 0
//        else if (change == money) 1
//        else if (change > money) 0
//        else countChangingPossible(change + coins.head, coins) +
//                          countChangingPossible(change, coins.tail)
//      }
//      countChangingPossible(0, coins)
    }
  }