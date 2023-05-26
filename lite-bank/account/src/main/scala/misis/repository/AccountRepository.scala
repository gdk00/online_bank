package misis.repository

import misis.model.Account

import scala.concurrent.Future

class AccountRepository(val accountId: Int, defAmount: Int){
    var account = Account(accountId, defAmount)

    def update(value: Int): Future[Account] = {
        if (value + account.amount >= 0)
            account = account.update(value)

        Future.successful(account)
    }
}
