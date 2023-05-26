package misis.kafka

import akka.actor.ActorSystem
import akka.kafka.scaladsl.Producer
import akka.stream.scaladsl.{Sink, Source}
import io.circe.generic.auto._
import io.circe.syntax._
import misis.WithKafka
import misis.model.{AccountUpdate, AccountUpdated}
import misis.repository.AccountRepository
import org.apache.kafka.clients.producer.ProducerRecord

import scala.concurrent.ExecutionContext

class Streams(repository: AccountRepository)(implicit val system: ActorSystem, executionContext: ExecutionContext)
    extends WithKafka {
    def group = s"fee"


}
