package misis.repository

import cats.arrow.Category
import misis.model.Cashback
import misis.model.ExternalTransactionComplete

import scala.concurrent.Future

class CashbackRepository(category: Int, cashback_percentage: Int) {
    def getCashback(transaction: ExternalTransactionComplete): Cashback = {
        if (category == transaction.categoryId)
            Cashback(is_allowed = true, value = cashback_percentage * transaction.value / 100)
        else
            Cashback(is_allowed = false, value = 0)
    }
}
