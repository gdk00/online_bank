package misis.cart.repository

import misis.cart.model._

import java.util.UUID
import scala.concurrent.Future

trait CartRepository {
    def list(): Future[List[Cart]]
    def get(id: UUID): Future[Cart]
    def create(cart: CreateCart): Future[Cart]
    def addItem(cartItem: AddCartItem): Future[Cart]
    def changeAmount(amount: ChangeAmount): Future[Cart]
}
