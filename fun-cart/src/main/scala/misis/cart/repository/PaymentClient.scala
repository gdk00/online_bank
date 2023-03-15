package misis.cart.repository

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import io.circe.generic.auto._
import io.circe.syntax._
import misis.cart.model.{CheckoutRequest, CheckoutResponse}

import scala.concurrent.{ExecutionContext, Future}

class PaymentClient(implicit val ec: ExecutionContext, actorSystem: ActorSystem) extends FailFastCirceSupport {
    def payment(checkout: CheckoutRequest): Future[Either[String, CheckoutResponse]] = {
        val request = HttpRequest(
            method = HttpMethods.PUT,
            uri = s"http://localhost:8081/user/money/withdraw",
            entity = HttpEntity(MediaTypes.`application/json`, checkout.asJson.noSpaces)
        )
        val future = for {
            response <- Http().singleRequest(request)
            result <- Unmarshal(response).to[CheckoutResponse]
                .map(res => Right(res))
                .recoverWith {
                    case _ => Unmarshal(response).to[String].map(Left(_))
                }
        } yield result

        future.recover(e => Left(e.getMessage))
    }
}
