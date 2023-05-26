package misis.model

import java.util.UUID

case class Account(id: Int, amount: Int) {
    def update(value: Int) = {
        if (amount + value >= 0)
            this.copy(amount = amount + value)
        else
            this.copy()
    }
}

trait Command
case class AccountUpdate(accountId: Int, value: Int)
case class ExternalAccountUpdate(srcAccountId: Int, dstAccountId: Int, value: Int, is_source: Boolean, categoryId: Int)

trait Event
case class AccountUpdated(accountId: Int, value: Int)

case class ExternalAccountUpdated(srcAccountId: Int, dstAccountId: Int, value: Int, is_source: Boolean, success: Boolean, categoryId: Int)

case class ExternalTransactionComplete(srcAccountId: Int, dstAccountId: Int, value: Int, categoryId: Int)
