package misis.cart.repository

import misis.cart.model.{CreateItem, Item, UpdateItem}

import java.util.UUID
import scala.collection.mutable
import scala.concurrent.{ExecutionContext, Future}

class ItemRepositoryInMemory(implicit val ec: ExecutionContext) extends ItemRepository {
    private val store = mutable.Map[UUID, Item]()

    override def list(): Future[Seq[Item]] = Future {
        store.values.toList
    }

    override def create(create: CreateItem): Future[Item] = Future {
        val item = Item(id = UUID.randomUUID(), name = create.name, price = create.price)
        store.put(item.id, item)
        item
    }

    override def update(update: UpdateItem): Future[Either[String, Item]] = Future {
        store.get(update.id).map { item =>
            val updated = item.copy(price = update.price)
            store.put(item.id, updated)
            Right(updated)
        }.getOrElse(Left("Не найден элемент"))
    }

    override def delete(id: UUID) = Future {
        store.remove(id)
    }

    override def get(id: UUID): Future[Item] = Future {
        store(id)
    }
}
