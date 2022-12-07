package misis.cart.route

import akka.http.scaladsl.model.{StatusCode, StatusCodes}
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import misis.cart.model.{CreateItem, UpdateItem}
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._
import misis.cart.repository.{Bigger100Exception, ItemRepository}

import scala.concurrent.ExecutionContext
import scala.util.{Failure, Success}

class ItemRoute(repository: ItemRepository)(implicit ec: ExecutionContext) extends FailFastCirceSupport {
    def route =
        (path("items") & get) {
            val list = repository.list()
            complete(list)
        } ~
            path("item") {
                (post & entity(as[CreateItem])) { newItem =>
                    complete(repository.create(newItem))
                }
            } ~
            path("item" / JavaUUID) { id =>
                get {
                    complete(repository.get(id))
                }
            } ~
            path("item") {
                (put & entity(as[UpdateItem])) { updateItem =>
                    onSuccess(repository.update(updateItem)) {
                        case Right(value) => complete(value)
                        case Left(s) => complete(StatusCodes.NotAcceptable, s)
                    }
                }
            } ~
            path("item" / JavaUUID) { id =>
                delete {
                    complete(repository.delete(id))
                }
            }
}
