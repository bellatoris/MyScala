package Observer
import rx.lang.scala.subscriptions.CompositeSubscription

import scala.concurrent.duration._
import scala.language.postfixOps
import rx.lang.scala.{Observable, Subscription}

/**
  * Created by DoogieMin on 2017. 2. 21..
  */
object Main {
  def main(args: Array[String]): Unit = {
    val ticks: Observable[Long] = Observable.interval(1 seconds)
    val evens: Observable[Long] = ticks.filter(_%2==0)
    val bufs: Observable[Seq[Long]] = evens.slidingBuffer(count = 2, skip = 1)
    val s = bufs.take(10).subscribe(println(_))
    println("hi")
    readLine()
    s.unsubscribe()
    //    val xs: Observable[Int] = Observable.from(List(3, 2, 1))
    //    val yss: Observable[Observable[Int]] =
    //      xs.map(x => Observable.interval(x seconds).map(_ => x).take(2))
    //    val zs: Observable[Int] = yss.concat
    //    val s = zs.subscribe(println(_))
    //    readLine()
    //    s.unsubscribe()
    //    val subscription = Subscription {
    //      println("bye, bye, I'm out finishing")
    //    }
    //    println(subscription.isUnsubscribed)
    //    subscription.unsubscribe()
    //    println(subscription.isUnsubscribed)
    //    subscription.unsubscribe()
    //    val a = Subscription { println("A") }
    //    val b = Subscription { println("B") }
    //
    //    val composite = CompositeSubscription(a, b)
    //    println(composite.isUnsubscribed)
    //    composite.unsubscribe()
    //    println(a.isUnsubscribed)
    //
    //    composite += Subscription{ println("C") }
    //    println(composite.isUnsubscribed)
    //    val c = Subscription{ println("D") }
    //    c.unsubscribe()
  }
}
