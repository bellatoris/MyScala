package Observer

import rx.lang.scala.Observable
import scala.concurrent.duration._

/**
  * Created by DoogieMin on 2017. 2. 21..
  */
object ObservableTimer extends App {
  val o = Observable.timer(1.seconds)
  o.subscribe(_ => println("Timeout!"))
  o.subscribe(_ => println("Another timeout!"))
  o.subscribe(_ => println("Timeout!"))
  o.subscribe(_ => println("Another timeout!"))
  o.subscribe(_ => println("Timeout!"))
  o.subscribe(_ => println("Another timeout!"))
  o.subscribe(_ => println("Timeout!"))
  o.subscribe(_ => println("Another timeout!"))
  println("hi")
  Thread.sleep(2000)
}
