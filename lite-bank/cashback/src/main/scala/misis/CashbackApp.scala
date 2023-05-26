package misis

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import misis.kafka.CashbackStreams
import misis.model.AccountUpdate
import misis.repository.CashbackRepository
import misis.route.Route
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object CashbackApp extends App {
    implicit val system: ActorSystem = ActorSystem("MyApp")
    implicit val ec = system.dispatcher
    val port = ConfigFactory.load().getInt("port")
    val category = ConfigFactory.load().getInt("category")
    val percentage = ConfigFactory.load().getInt("percentage")

    private val repository =
        new CashbackRepository(category, percentage)
    private val streams = new CashbackStreams(repository)

    private val route = new Route()
    Http().newServerAt("0.0.0.0", port).bind(route.routes)
}
