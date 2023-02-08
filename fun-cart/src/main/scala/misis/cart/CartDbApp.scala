package misis.cart

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import com.typesafe.config.{Config, ConfigFactory}
import misis.cart.db.InitDb
import misis.cart.repository._
import misis.cart.route._
import slick.jdbc.PostgresProfile.api._

object CartDbApp extends App {
    implicit val system: ActorSystem = ActorSystem("CartApp")
    implicit val ec = system.dispatcher
    implicit val db = Database.forConfig("database.postgres")
    val port = ConfigFactory.load().getInt("port")

    new InitDb().prepare()
    val items = new ItemRepositoryDb
    val client = new PaymentClient
    val carts = new CartRepositoryDb(client)
    val helloRoute = new HelloRoute().route
    val itemRoute = new ItemRoute(items).route
    val cartRoute = new CartRoute(carts).route

    Http().newServerAt("0.0.0.0", port).bind(helloRoute ~ itemRoute ~ cartRoute)
}
