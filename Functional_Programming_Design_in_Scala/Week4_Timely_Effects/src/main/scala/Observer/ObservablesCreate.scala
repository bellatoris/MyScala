package Observer

import rx.lang.scala.{Observable, Observer, Subscription}

/**
  * Created by DoogieMin on 2017. 2. 21..
  */
object ObservablesCreate extends App {
  val vms = Observable.create[String] { obs =>
    obs.onNext("JVM")
    obs.onNext("DartVM")
    obs.onNext("V8")
    obs.onCompleted()
    Subscription()
  }
  vms.subscribe(println(_), e => println(s"oops - $e"), () => println("Done"))
  println("hi")
  vms.subscribe(new Observer[String] {
    override def onNext(m: String) = println(s"Movies Watchlist - $m")
    override def onError(e: Throwable) = println(s"Ooops -$e!")
    override def onCompleted() = println(s"No more movies.")
  })

}
