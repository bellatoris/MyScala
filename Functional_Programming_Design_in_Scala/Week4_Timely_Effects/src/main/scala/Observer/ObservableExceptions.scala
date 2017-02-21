package Observer

import rx.lang.scala.Observable

/**
  * Created by DoogieMin on 2017. 2. 21..
  */
object ObservableExceptions extends App {
  val exc = new RuntimeException
  val o = Observable.from(List(1, 2)) ++ Observable.error(exc)
  o.subscribe(
    x => println(s"number $x"),
    t => println(s"an error occurred: $t")
  )

}
