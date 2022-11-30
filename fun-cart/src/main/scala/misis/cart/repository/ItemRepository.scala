package misis.cart.repository

import misis.cart.model._

import java.util.UUID

trait ItemRepository {
    def list(): List[Item]
    def get(id: UUID): Item
    def create(item: CreateItem): Item
    def update(item: UpdateItem): Option[Item]
    def delete(id: UUID)
}
