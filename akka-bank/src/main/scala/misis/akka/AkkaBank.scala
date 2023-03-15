package misis.akka

import akka.actor.{ActorSystem, Props}
import misis.akka.actor.{AccountActor, BankActor}
import misis.akka.model.Account._

object AkkaBank extends App {
    implicit val system: ActorSystem = ActorSystem("App")
    implicit val ec = system.dispatcher

    val bank = system.actorOf(Props(classOf[BankActor]), "Bank")
    val john = system.actorOf(Props(classOf[AccountActor]), "John")
    val mary = system.actorOf(Props(classOf[AccountActor]), "Mary")

    john ! Replenish(111, bank)
    mary ! Replenish(122, bank)
    mary ! Withdraw(23, bank)
    mary ! Withdraw(123, bank)
    mary ! Transfer(44, john, bank)
}
