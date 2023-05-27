package misis.model

import java.util.UUID

case class Account(id: Int, amount: Int) {
    def update(value: Int): Account = {
        this.copy(amount = amount + value)
    }
}

trait Command
case class CreateAccount(accountId: Int)
case class AccountUpdate(accountId: Int, value: Int, isFee: Boolean = false)
case class ExternalAccountUpdate(srcAccountId: Int, dstAccountId: Int, value: Int, is_source: Boolean, categoryId: Int)
case class FeeRequest(srcAccountId: Int, transactionValue: Int)
trait Event
case class AccountUpdated(accountId: Int, value: Int, success: Boolean, balance: Int)

case class ExternalAccountUpdated(
    srcAccountId: Int,
    dstAccountId: Int,
    value: Int,
    is_source: Boolean,
    var success: Boolean,
    categoryId: Int
)

case class ExternalTransactionComplete(srcAccountId: Int, dstAccountId: Int, value: Int, categoryId: Int)
