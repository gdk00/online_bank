package misis.cart.repository

import misis.cart.db.ItemDb._
import misis.cart.model.{CreateItem, Item, UpdateItem}
import slick.jdbc.PostgresProfile.api._

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

class ItemRepositoryDb(implicit val ec: ExecutionContext, db: Database) extends ItemRepository {
    override def list(): Future[Seq[Item]] = {
        db.run(itemTable.result)
    }

    override def get(id: UUID): Future[Item] = {
        db.run(itemTable.filter(i => i.id === id).result.head)
    }

    def find(id: UUID): Future[Option[Item]] = {
        db.run(itemTable.filter(i => i.id === id).result.headOption)
    }

    override def create(createItem: CreateItem): Future[Item] = {
        val item = Item(name = createItem.name, price = createItem.price)
        for {
            _ <- db.run(itemTable += item)
            res <- get(item.id)
        } yield res
    }

    override def update(item: UpdateItem): Future[Either[String, Item]] = {
        val query = itemTable
            .filter(_.id === item.id)
            .map(_.price)

        for {
            oldPriceOpt <- db.run(query.result.headOption)
            newPrice = item.price

            updatePrice = oldPriceOpt.map { oldPrice =>
                if (oldPrice > 100)
                    Left("Больше 100")
                else Right(oldPrice + newPrice)
            }.getOrElse(Left("Не найдено элемент"))
            future = updatePrice.map(price => db.run {
                query.update(price)
            }) match {
                case Right(future) => future.map(Right(_))
                case Left(s) => Future.successful(Left(s))
            }
            updated <- future
            res <- find(item.id)
        } yield updated.map(_ => res.get)
    }

    override def delete(id: UUID): Future[Unit] = {
        db.run(itemTable.filter(_.id === id).delete).map(_ => ())
    }
}
