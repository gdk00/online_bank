package misis.repository

import misis.model.{Account, CreateAccount, AccountUpdated}

import scala.concurrent.Future

class AccountRepository() {
    var accountList: List[Account] = List()

    def update(value: Int, accountId: Int): AccountUpdated = {
        accountList.indexWhere(acc => acc.id == accountId) match {
            case -1 =>
                AccountUpdated(accountId = accountId, value = value, success = false, balance = 0)
            case index =>
                val srcAccount = accountList(index)
                val dstAccount = Account(srcAccount.id, srcAccount.amount + value)
                if (dstAccount.amount >= 0) {
//                    accountList = accountList.updated(index, dstAccount)
                    accountList = accountList.map(acc => if (acc.id == dstAccount.id) dstAccount else acc)
                    println(
                        s"Аккаунт ${dstAccount.id} обновлен на сумму ${value}. Баланс: ${accountList(index).amount}"
                    )
                    AccountUpdated(accountId = accountId, value = value, success = true, balance = dstAccount.amount)
                } else
                    AccountUpdated(accountId = accountId, value = value, success = false, balance = srcAccount.amount)
        }
    }

    def create(accountId: Int): Account = {
        val newAccount = Account(accountId, amount = 0)
        if (accountList.exists(acc => acc.id == accountId))
            println(s"Account ${accountId} уже существует")
        else
            accountList = accountList :+ newAccount
        newAccount
    }
}
