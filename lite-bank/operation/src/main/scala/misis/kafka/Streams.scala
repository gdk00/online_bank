package misis.kafka

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import io.circe.generic.auto._
import misis.{TopicName, WithKafka}
import misis.model.{AccountUpdate, AccountUpdated, ExternalAccountUpdate, ExternalAccountUpdated}
import misis.repository.Repository

import scala.concurrent.{ExecutionContext, Future}

class Streams()(implicit val system: ActorSystem, executionContext: ExecutionContext) extends WithKafka {
    override def group: String = "operation"

    kafkaSource[ExternalAccountUpdated]
        .filter(command => (!command.is_source) && command.success)
        .mapAsync(1) { command =>
            Future.successful(produceCommand(AccountUpdate(command.dstAccountId, command.value)))
        }
        .to(Sink.ignore)
        .run()
}
