package misis.cart.db

import misis.cart.model.Item
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.util.UUID

object ItemDb {
    class ItemTable(tag: Tag) extends Table[Item](tag, "items"){
        val id = column[UUID]("id", O.PrimaryKey)
        val name = column[String]("name")
        val price = column[Int]("price")

        def * = (id, name, price) <> ((Item.apply _).tupled, Item.unapply)
    }

    val itemTable = TableQuery[ItemTable]
}
