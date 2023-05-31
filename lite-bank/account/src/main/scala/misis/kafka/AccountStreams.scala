package misis.kafka

import akka.actor.ActorSystem
import akka.stream.scaladsl.Sink
import io.circe.generic.auto._
import misis.WithKafka
import misis.model.{
    AccountUpdate,
    AccountUpdated,
    CreateAccount,
    ExternalAccountUpdate,
    ExternalAccountUpdated,
    ExternalTransactionComplete
}
import misis.repository.AccountRepository

import scala.concurrent
import scala.concurrent.{ExecutionContext, Future}
import scala.util.Failure

class AccountStreams(repository: AccountRepository)(implicit
    val system: ActorSystem,
    executionContext: ExecutionContext
) extends WithKafka {

    def group = s"account-${repository.startIdFrom}"

    kafkaSource[AccountUpdate]
        .filter(command => repository.accountList.exists(acc => acc.id == command.accountId))
        .mapAsync(1) { command =>
            Future.successful(repository.update(command.value, command.accountId, isFee = command.isFee))
        }
        .to(kafkaSink)
        .run()

    kafkaSource[CreateAccount]
        .filter(command =>
            command.accountId <= repository.startIdFrom && command.accountId + 10000 > repository.startIdFrom
        )
        .mapAsync(1) { command =>
            println(s"создан аккаунт id = ${command.accountId}")
            Thread.sleep(2000)
            Future.successful(repository.create(command.accountId))

        }
        .to(kafkaSink)
        .run()

    kafkaSource[ExternalAccountUpdate]
        .filter(command => command.is_source && repository.accountList.exists(acc => acc.id == command.srcAccountId))
        .mapAsync(1) { command =>
            val accountUpdated = repository.update(-command.value, command.srcAccountId)

            val externalAccountUpdated = ExternalAccountUpdated(
                command.srcAccountId,
                command.dstAccountId,
                command.value,
                is_source = false,
                success = false,
                categoryId = command.categoryId
            )

            if (accountUpdated.success) {
                println(
                    s"C аккаунта ${command.srcAccountId} (баланс: ${accountUpdated.balance}) переведена сумма " +
                        s"${command.value} на аккаунт ${command.dstAccountId}"
                )
                externalAccountUpdated.success = true

            } else {
                println(
                    s"C аккаунта ${command.srcAccountId} не может быть переведена сумма " +
                        s"${command.value} на аккаунт ${command.dstAccountId} " +
                        s"Баланс: ${accountUpdated.balance}"
                )
            }
            produceCommand(externalAccountUpdated)
            Future.successful()
        }
        .to(Sink.ignore)
        .run()

    kafkaSource[ExternalAccountUpdated]
        .filter(command =>
            !command.is_source && command.success && repository
                .accountList
                .exists(acc => acc.id == command.dstAccountId)
        )
        .mapAsync(1) { command =>
            val accountUpdated = repository.update(command.value, command.dstAccountId)
            println(
                s"Аккаунт ${command.dstAccountId} пополнен на  " +
                    s"${command.value} переводом с аккаунта ${command.dstAccountId} (баланс: ${accountUpdated.balance})"
            )
            produceCommand(
                ExternalTransactionComplete(
                    command.srcAccountId,
                    command.dstAccountId,
                    command.value,
                    categoryId = command.categoryId
                )
            )
            Future.successful()

        }
        .to(Sink.ignore)
        .run()
}
