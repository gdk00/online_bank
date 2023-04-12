package misis.db

import misis.db.AccountDb.AccountTable
import scala.concurrent.{ExecutionContext, Future}
import slick.jdbc.PostgresProfile.api._

class InitDB()(implicit val ec: ExecutionContext, db: Database) {
    def prepare(): Future[_] = {
        db.run(AccountTable.schema.createIfNotExists)
    }
}
