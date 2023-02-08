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
    def payment(checkout: CheckoutRequest): Future[CheckoutResponse] = {
        val request = HttpRequest(
            method = HttpMethods.PUT,
            uri = s"http://localhost:8080/user/money/withdraw",
            entity = HttpEntity(MediaTypes.`application/json`, checkout.asJson.noSpaces)
        )
        for {
            response <- Http().singleRequest(request)
            result <- Unmarshal(response).to[CheckoutResponse]
        } yield result

    }

}
