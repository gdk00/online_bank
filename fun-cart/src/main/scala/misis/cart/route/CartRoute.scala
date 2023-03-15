package misis.cart.route

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import misis.cart.model._
import misis.cart.repository.CartRepository

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class CartRoute(repository: CartRepository)(implicit ec: ExecutionContext) extends FailFastCirceSupport {
    def route =
        (path("cart") & get) {
            val list = repository.list()
            complete(list)
        } ~
            path("cart") {
                (post & entity(as[CreateCart])) { newItem =>
                    complete(repository.create(newItem))
                }
            } ~
            path("cart" / JavaUUID) { id =>
                get {
                    complete(repository.get(id))
                }
            } ~
            path("cart"/ "checkout" / JavaUUID / JavaUUID) { case (id, accountId) =>
                put {
                    onComplete(repository.checkout(id, accountId)) {
                        case Success(Right(value)) => complete(value)
                        case Success(Left(error)) => complete(StatusCodes.NotAcceptable, s"Произошла ошибка при списании средств $error")
                        case Failure(error) => complete(StatusCodes.NotAcceptable, s"Произошла ошибка $error")
                    }
                }
            }

}
