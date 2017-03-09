import java.io.FileReader
import java.io.FileNotFoundException
import java.io.IOException
import java.net.URL
import java.net.MalformedURLException


object Main {
  def main(args: Array[String]) {
    val half = (n: Int) => 
      if (n % 2 == 0)
        n / 2
      else 
        throw new RuntimeException("n must be even")

    try {
      val f = new FileReader("input.txt")
    } catch {
      case ex: FileNotFoundException => 
      case ex: IOException => 
    }

    val file = new FileReader("input.txt")

    try {
      //
    } finally {
      file.close()
    }

    def urlFor(path: String) = 
      try {
        new URL(path)
      } catch {
        case e: MalformedURLException => new URL("http://www.scala-lang.org")
      }
  }
}
