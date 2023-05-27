package misis

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import misis.kafka.AccountStreams
import misis.model.AccountUpdate
import misis.repository.AccountRepository
import misis.route.Route
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object AccountApp extends App {
    implicit val system: ActorSystem = ActorSystem("App")
    implicit val ec = system.dispatcher
    val port = ConfigFactory.load().getInt("port")

    private val repository = new AccountRepository()
    private val streams = new AccountStreams(repository)

    implicit val commandTopicName: TopicName[AccountUpdate] = streams.simpleTopicName[AccountUpdate]

    private val route = new Route()
    Http().newServerAt("0.0.0.0", port).bind(route.routes)
}
