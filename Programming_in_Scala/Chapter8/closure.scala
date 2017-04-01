object Main {
  def main(args: Array[String]): Unit = {
    var more = 1
    val addMore = (x: Int) => x + more
    println(addMore(10))
    more = 9999
    println(addMore(11))

    def addMore2(x: Int): Int = {
      x + more
    }
    more = 1 
    println(addMore2(11))
  }
}
