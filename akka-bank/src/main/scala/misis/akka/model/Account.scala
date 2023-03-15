package misis.akka.model

import akka.actor.ActorRef

import java.util.UUID

object Account {
    type Client = String

    case class State(id: UUID = UUID.randomUUID(), client: Client, amount: Int) {
        def update(diff: Int): Either[String, State] = {
            if (amount + diff >= 0)
                Right(copy(amount = amount + diff))
            else
                Left("Недостаточно средств на счете")
        }
    }

    object State {
        def empty(client: Client) = State(client = client, amount = 0)
    }

    trait Command
    case class Replenish(amount: Int, replyTo: ActorRef) extends Command
    case class Withdraw(amount: Int, replyTo: ActorRef) extends Command
    case class Transfer(amount: Int, transferTo: ActorRef, replyTo: ActorRef) extends Command

    trait Event
    case class UpdateSuccessful(client: Client, amount: Int) extends Event
    case class UpdateFailed(client: Client, error: String) extends Event
}
