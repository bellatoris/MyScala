package Observer
import rx.lang.scala._

import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.util.{Failure, Success}
import org.apache.commons.io._
import org.apache.commons.io.monitor._
/**
  * Created by DoogieMin on 2017. 2. 21..
  */
object ObservablesCreateFuture extends App {
  val f = Future { "Back to the Future(s)" }
  val o = Observable.create[String] { obs =>
//    f.foreach { case s => obs.onNext(s); obs.onCompleted() }
//    f.failed foreach { case t => obs.onError(t) }
    f onComplete {
      case Success(s) => obs.onNext(s); obs.onCompleted()
      case Failure(er) => obs.onError(er)
    }
    Subscription()
  }
  o.subscribe(println(_))

}

object ObservableSubscriptions extends App {
  def modified(directory: String): Observable[String] = {
    Observable.create { observer =>
      val fileMonitor = new FileAlterationMonitor(1000)
      val fileObs = new FileAlterationObserver(directory)
      val fileLis = new FileAlterationListenerAdaptor {
        override def onFileChange(file: java.io.File): Unit = {
          observer.onNext(file.getName)
        }
      }
      fileObs.addListener(fileLis)
      fileMonitor.addObserver(fileObs)
      fileMonitor.start()
      Subscription { fileMonitor.stop() }
    }
  }

  println(s"starting to monitor files")
  val sub = modified(".").subscribe(n => println(s"$n modified"))
  println(s"please modify and save a file")
  Thread.sleep(10000)
  sub.unsubscribe()
  println(s"monitoring done")
}

