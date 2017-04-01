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
    
    val firstArg = if (args.length > 0) args(0) else ""

    firstArg match {
      case "salt" => println("pepper")
      case "chips" => println("salsa")
      case "eggs" => println("bacon")
      case _ => println("huh")
    }

    // The result of match is value!
    val freind = 
      firstArg match {
        case "salt" => "pepper"
        case "chips" => "salsa"
        case "eggs" => "bacon"
        case _ => "huh"
    }

    println(friend)
  }
}
