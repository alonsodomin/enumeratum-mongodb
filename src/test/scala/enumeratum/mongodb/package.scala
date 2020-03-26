package enumeratum

import java.net.ServerSocket

package object mongodb {

  import java.util.concurrent.atomic.AtomicReference

  import org.mongodb.scala.Observer

  import scala.concurrent.Promise

  import scala.concurrent.Future

  import org.mongodb.scala.Observable

  def findFreePort(): Int = {
    val socket = new ServerSocket(0)
    try {
      socket.getLocalPort()
    } finally {
      socket.close()
    }
  }

  implicit class ObservableSyntax[A](self: Observable[A]) {
    def toFuture: Future[A] = {
      val promise = Promise[A]()
      self.subscribe(new Observer[A] {
        val value = new AtomicReference[A]()

        def onComplete(): Unit =
          promise.success(value.get())

        def onNext(item: A): Unit =
          value.set(item)

        def onError(error: Throwable): Unit =
          promise.failure(error)

      })
      promise.future
    }
  }

}
