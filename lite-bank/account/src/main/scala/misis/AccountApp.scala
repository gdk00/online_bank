package misis

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import misis.kafka.Streams
import misis.model.AccountUpdate
import misis.repository.Repository
import misis.route.Route
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object AccountApp extends App {
    implicit val system: ActorSystem = ActorSystem("App")
    implicit val ec = system.dispatcher
    val port = ConfigFactory.load().getInt("port")
    val accountId = ConfigFactory.load().getInt("account.id")
    val defAmount = ConfigFactory.load().getInt("account.amount")

    private val repository = new Repository(accountId, defAmount)
    private val streams = new Streams(repository)

    implicit val commandTopicName: TopicName[AccountUpdate] = streams.simpleTopicName[AccountUpdate]
//    val rand = new scala.util.Random
//    streams.produceCommand(AccountUpdate(1, rand.nextInt(1000)))
//    streams.produceCommand(AccountUpdate(2, rand.nextInt(1000)))

    private val route = new Route()
    Http().newServerAt("0.0.0.0", port).bind(route.routes)
}
