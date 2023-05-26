package misis.kafka

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import io.circe.generic.auto._
import misis.{TopicName, WithKafka}
import misis.model.{AccountUpdate, AccountUpdated, ExternalAccountUpdate, ExternalTransactionComplete}
import misis.repository.CashbackRepository
import scala.concurrent.{ExecutionContext, Future}

class CashbackStreams(repository: CashbackRepository)(implicit val system: ActorSystem, executionContext: ExecutionContext) extends WithKafka {
    override def group: String = "cashback"

    kafkaSource[ExternalTransactionComplete]
        .mapAsync(1) { command =>
            val cashback = repository.getCashback(command)
            if (cashback.is_allowed) {
                println(s"Кэшбек для категории ${command.categoryId} акканта ${command.srcAccountId} начислен")
                Future.successful(produceCommand(AccountUpdate(command.srcAccountId, cashback.value)))
            }
          else {
                println(s"Кэшбек для категории ${command.categoryId} акканта ${command.srcAccountId} не предназначен")
                Future.successful(command)
            }
        }
        .to(Sink.ignore)
        .run()
}