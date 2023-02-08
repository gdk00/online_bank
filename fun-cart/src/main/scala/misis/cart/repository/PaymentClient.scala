package misis.cart.repository

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpMethods, HttpRequest, MediaTypes}
import akka.http.scaladsl.unmarshalling.Unmarshal
import misis.cart.model.Checkout
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

import scala.concurrent.{ExecutionContext, Future}

class PaymentClient (implicit val ec: ExecutionContext, actorSystem: ActorSystem){
    def payment(checkout: Checkout): Future[String] = {
        val request = HttpRequest(
            method = HttpMethods.PUT,
            uri = s"http://localhost:8080/user/money/withdraw",
            entity = HttpEntity(MediaTypes.`application/json`, checkout.asJson.noSpaces)
        )
        val response = Http().singleRequest(request)
        response.flatMap(Unmarshal(_).to[String])
    }

}
