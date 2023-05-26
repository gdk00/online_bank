package misis

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import misis.kafka.OperationStreams
import misis.model.{AccountUpdate, ExternalAccountUpdated}
import misis.repository.OperationRepository
import misis.route.OperationRoute
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object OperationApp extends App {
    implicit val system: ActorSystem = ActorSystem("OperationApp")
    implicit val ec = system.dispatcher
    val port = ConfigFactory.load().getInt("port")

    private val streams = new OperationStreams()

    private val repository = new OperationRepository(streams)

    private val route = new OperationRoute(streams, repository)
    Http().newServerAt("0.0.0.0", port).bind(route.routes)
}
