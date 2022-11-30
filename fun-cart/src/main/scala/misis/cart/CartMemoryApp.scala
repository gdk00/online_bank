package misis.cart

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import misis.cart.repository.ItemRepositoryInMemory
import misis.cart.route._

object CartMemoryApp extends App {
    implicit val system: ActorSystem = ActorSystem("CartApp")
    implicit val ec = system.dispatcher
    val repository = new ItemRepositoryInMemory
    val helloRoute = new HelloRoute().route
    val itemRoute = new ItemRoute(repository).route

    Http().newServerAt("0.0.0.0", 8080).bind(helloRoute ~ itemRoute)
}
