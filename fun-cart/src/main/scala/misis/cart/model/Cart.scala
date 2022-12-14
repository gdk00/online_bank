package misis.cart.model

import java.util.UUID

case class CartItem(id: UUID = UUID.randomUUID(), item: Item, amount: Int, price: Int)
case class Cart(id: UUID = UUID.randomUUID(), items: List[CartItem], client: String)

case class CreateCart(client: String)
case class AddCartItem(cartItemId: UUID)
case class ChangeAmount(cartItemId: UUID, amount: Int)