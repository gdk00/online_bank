package misis.repository

import cats.arrow.Category
import misis.model.ExternalTransactionComplete

import scala.collection.mutable
import scala.collection.mutable.Map
import scala.concurrent.Future

class FeeRepository(limit: Int, percentage: Int) {
    var feeMap: mutable.Map[Int, Int] = mutable.Map()

    def getFee(transactionValue: Int): Int = {
        transactionValue * percentage / 100
    }

    def updateLimit(accountId: Int, transactionValue: Int): Int = {
        if (feeMap.contains(accountId)) {
            feeMap(accountId) = feeMap(accountId) - transactionValue
        } else
            feeMap(accountId) = limit - transactionValue
        feeMap(accountId)
    }
}
