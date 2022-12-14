package misis.cart.repository
import misis.cart.db.CartDb._
import misis.cart.db.ItemDb._
import misis.cart.model.{AddCartItem, Cart, CartItem, ChangeAmount, CreateCart}
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

class CartRepositoryDb(implicit val ec: ExecutionContext, db: Database) extends CartRepository {
    override def list(): Future[List[Cart]] = ???

    override def get(id: UUID): Future[Cart] = {
        for {
            cartEntity <- db.run(cartTable.filter(_.id === id).result.head)
            cartItemEntities <- db.run(cartItemTable.filter(_.cartId === id).result)
            items <- Future.sequence( cartItemEntities.map { cie =>
                db.run(itemTable.filter(_.id === cie.id).result.head)
                    .map(item => item -> cie)
            })
        } yield Cart(
            id = cartEntity.id,
            items = items.map { case (item, cie) =>
                CartItem(
                    id = cie.id,
                    item = item,
                    amount = cie.amount,
                    price = cie.price
                )
            }.toList,
            client = cartEntity.client
        )
    }

    override def create(cart: CreateCart): Future[Cart] = ???

    override def addItem(cartItem: AddCartItem): Future[Cart] = ???

    override def changeAmount(amount: ChangeAmount): Future[Cart] = ???
}
