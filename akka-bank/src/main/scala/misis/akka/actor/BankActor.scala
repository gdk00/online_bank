package misis.akka.actor

import akka.actor.Actor
import misis.akka.model.Account.{UpdateFailed, UpdateSuccessful}

class BankActor extends Actor {
    override def receive: Receive = {
        case UpdateSuccessful(client, amount) => println(s"Значение счета $client успешно обновлено $amount")
        case UpdateFailed(client, error) => println(s"Проблема с операцией над счетом $client: $error")
    }
}
