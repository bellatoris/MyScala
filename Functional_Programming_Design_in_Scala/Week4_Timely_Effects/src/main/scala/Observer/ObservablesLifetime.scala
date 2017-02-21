package Observer

import rx.lang.scala.{Observable, Observer}

/**
  * Created by DoogieMin on 2017. 2. 21..
  */
object ObservablesLifetime extends App {
  val classics = List("Good, bad, ugly", "Titanic", "Die Hard")
  val movies = Observable.from(classics)
  movies.subscribe(new Observer[String] {
    override def onNext(m: String) = println(s"Movies Watchlist - $m")
    override def onError(e: Throwable) = println(s"Ooops -$e!")
    override def onCompleted() = println(s"No more movies.")
  })
}
