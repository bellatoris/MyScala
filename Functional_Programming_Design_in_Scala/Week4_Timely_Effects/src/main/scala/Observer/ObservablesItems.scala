package Observer

import rx.lang.scala._

/**
  * Created by DoogieMin on 2017. 2. 21..
  */
object ObservablesItems extends App {
  val o = Observable.from(List("Pascal", "Java", "Scala"))
  o.subscribe(name => println(s"learned the $name language"))
  println("hi")
  o.subscribe(name => println(s"forgot the $name language"))
}
