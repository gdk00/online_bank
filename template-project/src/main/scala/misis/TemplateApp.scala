package misis

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import com.typesafe.config.{Config, ConfigFactory}

object TemplateApp.scala extends App {
    implicit val system: ActorSystem = ActorSystem("App")
    implicit val ec = system.dispatcher
    val port = ConfigFactory.load().getInt("port")

    val route: Route = ???

    Http().newServerAt("0.0.0.0", port).bind(route)
}
