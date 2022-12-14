package misis.cart

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import misis.cart.db.InitDb
import misis.cart.repository._
import misis.cart.route._
import slick.jdbc.PostgresProfile.api._

object CartDbApp extends App {
    implicit val system: ActorSystem = ActorSystem("CartApp")
    implicit val ec = system.dispatcher
    implicit val db = Database.forConfig("database.postgres")

    new InitDb().prepare()
    val items = new ItemRepositoryDb
    val carts = new CartRepositoryDb
    val helloRoute = new HelloRoute().route
    val itemRoute = new ItemRoute(items).route
    val cartRoute = new CartRoute(carts).route

    Http().newServerAt("0.0.0.0", 8080).bind(helloRoute ~ itemRoute ~ cartRoute)
}
