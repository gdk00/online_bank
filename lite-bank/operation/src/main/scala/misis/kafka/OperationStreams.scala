package misis.kafka

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import io.circe.generic.auto._
import misis.{TopicName, WithKafka}
import misis.model.{AccountUpdate, AccountUpdated, ExternalAccountUpdate, ExternalAccountUpdated}
import misis.repository.AccountRepository

import scala.concurrent.{ExecutionContext, Future}

class OperationStreams()(implicit val system: ActorSystem, executionContext: ExecutionContext) extends WithKafka {
    override def group: String = "operation"

    kafkaSource[ExternalAccountUpdated]
        .filter(command => (!command.is_source) && command.success)
        .mapAsync(1) { command =>
            Future.successful(produceCommand(ExternalAccountUpdate(command.srcAccountId, command.dstAccountId, command.value,
                is_source = false, categoryId = command.categoryId)))
        }
        .to(Sink.ignore)
        .run()
}
