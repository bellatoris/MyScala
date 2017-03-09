object Main {
  def main(args: Array[String]) = {
    val filesHere = (new java.io.File(".")).listFiles

    for (file <- filesHere) {
      println(file)
    }

    for (file <- filesHere if file.getName.endsWith(".scala"))
      println(file)

    for (
      file <- filesHere
      if file.isFile
      if file.getName.endsWith(".scala")
      ) println(file)

    def fileLines(file: java.io.File) = 
      scala.io.Source.fromFile(file).getLines().toList

    def grep(pattern: String) = 
      for (
        file <- filesHere
        if file.getName.endsWith(".scala");
        line <- fileLines(file)
        if line.trim.matches(pattern)
      ) println(file +": "+ line.trim)

    grep(".*for.*")

    def grep2(pattern: String) = 
      for {
        file <- filesHere
        if file.getName.endsWith(".scala");
        line <- fileLines(file)
        trim = line.trim
        if trim.matches(pattern)
      } println(file +": "+ trim)

    def scalaFiles = 
      for {
        file <- filesHere
        if file.getName.endsWith(".scala")
      } yield file

    val forLineLengths = 
      for {
        file <- filesHere
        if file.getName.endsWith(".scala")
        line <- fileLines(file)
        trimmed = line. trim
        if trimmed.matches(".*for.*")
      } yield trimmed.length

    for {
      length <- forLineLengths
    } println(length)
  }
}
