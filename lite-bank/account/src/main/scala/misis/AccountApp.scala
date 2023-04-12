package misis

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import com.typesafe.config.ConfigFactory
import misis.db.InitDB
import misis.route.Route
import slick.jdbc.PostgresProfile.api._

object AccountApp extends App {
    implicit val system: ActorSystem = ActorSystem("App")
    implicit val ec = system.dispatcher

    implicit val db = Database.forConfig("database.postgres")

    new InitDB()(ec, db).prepare()
    private val route = new Route()

    val port = ConfigFactory.load().getInt("port")

    Http().newServerAt("0.0.0.0", port).bind(route.routes)
}
