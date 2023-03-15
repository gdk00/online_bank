package misis.cart.repository
import misis.cart.db.CartDb._
import misis.cart.db.ItemDb._
import misis.cart.model.{Cart, CartItem, CheckoutRequest, CheckoutResponse, CreateCart}
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

class CartRepositoryDb(client: PaymentClient)(implicit val ec: ExecutionContext, db: Database) extends CartRepository {
    override def list(): Future[List[Cart]] = ???

    override def get(id: UUID): Future[Cart] = {
        for {
            cartEntity <- db.run(cartTable.filter(_.id === id).result.head)
            items <- db.run(cartItemTable.join(itemTable).on { case (cartItem, item) => cartItem.itemId === item.id }
                .filter { case (cartItem, _) => cartItem.cartId === id }
                .result
            )
        } yield Cart(
            id = cartEntity.id,
            items = items.map { case (cartItem, item) =>
                CartItem(
                    id = cartItem.id,
                    item = item,
                    amount = cartItem.amount,
                    price = cartItem.price
                )
            }.toList,
            client = cartEntity.client
        )
    }

    override def create(createCart: CreateCart): Future[Cart] = {
        val cartEntity = CartEntity(client = createCart.client)
        for {
            items <- Future.sequence(
                createCart
                    .items
                    .map(cartItem => db.run(itemTable.filter(_.id === cartItem.itemId).result.head.map(_ -> cartItem)))
            )
            cartItemEntities = items.map { case (item, cartItem) =>
                CartItemEntity(itemId = item.id, cartId = cartEntity.id, amount = cartItem.amount, price = item.price)
            }
            _ <- db.run(
                DBIO.sequence(
                    (cartTable += cartEntity) :: cartItemEntities.map(cartItem => cartItemTable += cartItem)
                )
            )
            cart <- get(cartEntity.id)
        } yield cart
    }

    def checkout(id: UUID, accountId: UUID): Future[Either[String, CheckoutResponse]] = {
        for {
            cart <- get(id)
            response <- client.payment(CheckoutRequest(accountId, cart.items.map(_.price).sum))
        } yield response
    }
}
