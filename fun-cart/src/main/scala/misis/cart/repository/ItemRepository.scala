package misis.cart.repository

import misis.cart.model._

import java.util.UUID
import scala.concurrent.Future

trait ItemRepository {
    def list(): Future[List[Item]]
    def get(id: UUID): Future[Item]
    def create(item: CreateItem): Future[Item]
    def update(item: UpdateItem): Future[Option[Item]]
    def delete(id: UUID): Future[Unit]
}
