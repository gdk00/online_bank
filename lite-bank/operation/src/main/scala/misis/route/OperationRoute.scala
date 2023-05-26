package misis.route

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives._
import io.circe.generic.auto._
import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport
import misis.TopicName
import misis.kafka.OperationStreams
import misis.model.{AccountUpdate, TransferStart}
import misis.repository.OperationRepository

import scala.concurrent.ExecutionContext


class OperationRoute(streams: OperationStreams, repository: OperationRepository)(implicit ec: ExecutionContext) extends FailFastCirceSupport {

    implicit val commandTopicName: TopicName[AccountUpdate] = streams.simpleTopicName[AccountUpdate]

    def routes =
        (path("hello") & get) {
            complete("ok")
        } ~
            (path("update" / IntNumber / IntNumber) { (accountId, value) =>
                val command = AccountUpdate(accountId, value)
                streams.produceCommand(command)
                complete(command)
            }) ~
            (path("transfer") & post & entity(as[TransferStart])) { transfer =>
                repository.transfer(transfer)
                complete(transfer)
            }
}


