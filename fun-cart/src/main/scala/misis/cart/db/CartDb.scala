package misis.cart.db

import misis.cart.model._
import slick.jdbc.PostgresProfile.api._
import slick.lifted.Tag

import java.util.UUID

object CartDb {
    case class CartItemEntity(id: UUID = UUID.randomUUID(), itemId: UUID, cartId: UUID, amount: Int, price: Int)

    class CartItemTable(tag: Tag) extends Table[CartItemEntity](tag, "cart_items"){
        val id = column[UUID]("id", O.PrimaryKey)
        val itemId = column[UUID]("item_id")
        val cartId = column[UUID]("cart_id")
        val amount = column[Int]("amount")
        val price = column[Int]("price")

        def * = (id, itemId, cartId, amount, price) <> ((CartItemEntity.apply _).tupled, CartItemEntity.unapply)
    }

    case class CartEntity(id: UUID = UUID.randomUUID(), client: String)

    class CartTable(tag: Tag) extends Table[CartEntity](tag, "carts"){
        val id = column[UUID]("id", O.PrimaryKey)
        val client = column[String]("client")

        def * = (id, client) <> ((CartEntity.apply _).tupled, CartEntity.unapply)
    }

    val cartItemTable = TableQuery[CartItemTable]
    val cartTable = TableQuery[CartTable]

}
