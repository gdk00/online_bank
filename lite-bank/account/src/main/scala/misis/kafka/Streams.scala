package misis.kafka

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import io.circe.generic.auto._
import misis.WithKafka
import misis.model.{AccountUpdate, AccountUpdated, ExternalAccountUpdate, ExternalAccountUpdated}
import misis.repository.Repository

import scala.concurrent.{ExecutionContext, Future}

class Streams(repository: Repository)(implicit val system: ActorSystem, executionContext: ExecutionContext)
    extends WithKafka {

    def group = s"account-${repository.accountId}"

    kafkaSource[AccountUpdate]
        .filter(command => repository.account.id == command.accountId && repository.account.amount + command.value >= 0)
        .mapAsync(1) { command =>
            repository.update(command.value).map(_ => AccountUpdated(command.accountId, command.value))
        }
        .to(kafkaSink)
        .run()

    kafkaSource[ExternalAccountUpdate]
        .filter(command => repository.account.id == command.srcAccountId && command.is_source)
        .mapAsync(1) { command =>
            if (repository.account.amount - command.value >= 0) {
                println(
                    s"C аккаунта ${command.srcAccountId} переведена сумма " +
                        s"${command.value} на аккаунт ${command.dstAccountId} " +
                        s"Баланс: ${repository.account.amount - command.value}"
                )

                repository
                    .update(-command.value)
                    .map(_ =>
                        produceCommand(
                            ExternalAccountUpdated(
                                command.srcAccountId,
                                command.dstAccountId,
                                command.value,
                                is_source = false,
                                success = true
                            )
                        )
                    )
            } else {
                println(
                    s"C аккаунта ${command.srcAccountId} не может быть переведена сумма " +
                        s"${command.value} на аккаунт ${command.dstAccountId} " +
                        s"Баланс: ${repository.account.amount}"
                )

                Future.successful(
                    produceCommand(
                        ExternalAccountUpdated(
                            command.srcAccountId,
                            command.dstAccountId,
                            command.value,
                            is_source = false,
                            success = false
                        )
                    )
                )
            }

        }
        .to(Sink.ignore)
        .run()

    kafkaSource[AccountUpdated]
        .filter(event => repository.account.id == event.accountId)
        .map { e =>
            println(s"Аккаунт ${e.accountId} обновлен на сумму ${e.value}. Баланс: ${repository.account.amount}")
            e
        }
        .to(Sink.ignore)
        .run()
}
