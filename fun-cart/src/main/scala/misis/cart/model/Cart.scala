package misis.cart.model

import java.util.UUID

case class CartItem(id: UUID = UUID.randomUUID(), item: Item, amount: Int, price: Int)
case class Cart(id: UUID = UUID.randomUUID(), items: List[CartItem], client: String)

case class CreateCartItem(itemId: UUID, amount: Int)
case class CreateCart(client: String, items: List[CreateCartItem])

case class Checkout(id: UUID, money: Int)
