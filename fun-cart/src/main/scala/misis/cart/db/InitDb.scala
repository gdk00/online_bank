package misis.cart.db

import misis.cart.db.CartDb.{cartItemTable, cartTable}
import misis.cart.db.ItemDb.itemTable
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}


class InitDb(implicit val ec: ExecutionContext, db: Database) {
    def prepare(): Future[_] = {
        db.run(itemTable.schema.createIfNotExists)
        db.run(cartTable.schema.createIfNotExists)
        db.run(cartItemTable.schema.createIfNotExists)
    }
}
