package misis.akka.actor

import akka.actor.Actor
import misis.akka.model.Account._

class AccountActor extends Actor {
    var state: State = State.empty(self.path.name)

    override def receive: Receive = {
        case Replenish(amount, replyTo) => state.update(amount) match {
            case Right(newState) =>
                state = newState
                replyTo ! UpdateSuccessful(state.client, state.amount)
            case Left(error) =>
                replyTo ! UpdateFailed(state.client, error)
        }

        case Withdraw(amount, replyTo) => state.update(-amount) match {
            case Right(newState) =>
                state = newState
                replyTo ! UpdateSuccessful(state.client, state.amount)
            case Left(error) =>
                replyTo ! UpdateFailed(state.client, error)
        }

        case Transfer(amount, transferTo, replyTo) => state.update(-amount) match {
            case Right(newState) =>
                state = newState
                replyTo ! UpdateSuccessful(state.client, state.amount)
                transferTo ! Replenish(amount, replyTo)
            case Left(error) =>
                replyTo ! UpdateFailed(state.client, error)
        }
    }
}
