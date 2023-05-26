package misis.repository

import misis.TopicName
import misis.kafka.OperationStreams
import misis.model.{AccountUpdate, ExternalAccountUpdate, TransferStart}
import io.circe.generic.auto._

import scala.concurrent.Future

class OperationRepository(streams: OperationStreams) {
    implicit val commandTopicName: TopicName[ExternalAccountUpdate] = streams.simpleTopicName[ExternalAccountUpdate]

    def transfer(transfer: TransferStart): Unit = {
        if (transfer.value > 0) {
            //            streams.produceCommand(AccountUpdate(transfer.sourceId, -transfer.value))
//                        streams.produceCommand(AccountUpdate(transfer.destinationId, transfer.value))
            streams.produceCommand(
                ExternalAccountUpdate(transfer.sourceId, transfer.destinationId, transfer.value, is_source = true,
                    categoryId = transfer.categoryId)
            )
        }
    }
}
