package misis.cart

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import misis.cart.db.InitDb
import misis.cart.repository.ItemRepositoryDb
import slick.jdbc.PostgresProfile.api._
import misis.cart.route._

object CartDbApp extends App {
    implicit val system: ActorSystem = ActorSystem("CartApp")
    implicit val ec = system.dispatcher
    implicit val db = Database.forConfig("database.postgres")

    new InitDb().prepare()
    val repository = new ItemRepositoryDb
    val helloRoute = new HelloRoute().route
    val itemRoute = new ItemRoute(repository).route

    Http().newServerAt("0.0.0.0", 8080).bind(helloRoute ~ itemRoute)
}
