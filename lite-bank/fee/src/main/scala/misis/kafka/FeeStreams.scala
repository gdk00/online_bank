package misis.kafka

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import io.circe.generic.auto._
import misis.{TopicName, WithKafka}
import misis.model.{AccountUpdate, FeeRequest, ExternalAccountUpdate, ExternalTransactionComplete}
import misis.repository.FeeRepository
import scala.concurrent.{ExecutionContext, Future}

class FeeStreams(repository: FeeRepository)(implicit val system: ActorSystem, executionContext: ExecutionContext)
    extends WithKafka {
    override def group: String = "cashback"

    kafkaSource[FeeRequest]
        .mapAsync(1) { command =>
            val limit: Int = repository.updateLimit(command.srcAccountId, command.transactionValue)
            if (limit < 0) {
                val fee = repository.getFee(command.transactionValue)
                println(
                    s"Превышен лимит для совершения переводов аккаунта ${command.srcAccountId}. Списана комиссия ${fee}."
                )
                produceCommand(AccountUpdate(command.srcAccountId, -fee, isFee = true))
            } else {
                println(s"Лимит для переводов без комиссии аккаунта ${command.srcAccountId}: ${limit}")
            }
            Future.successful()
        }
        .to(Sink.ignore)
        .run()
}
