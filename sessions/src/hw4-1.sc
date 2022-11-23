abstract class IntSet {
    def value: Int
    def contains(e: Int): Boolean
    def include(e: Int): IntSet
    def union(e: IntSet): IntSet
}

case class Element(val value: Int, left: IntSet, right: IntSet) extends IntSet {
    override def contains(e: Int): Boolean = {
        if (e == value) true
        else if (e < value) left.contains(e)
        else right.contains(e)
    }
    override def include(e: Int): IntSet = {
        if (e == value) this
        else if (e < value) new Element(value, left.include(e), right)
        else new Element(value, left, right.include(e))
    }
    override def union(e: IntSet): IntSet = ???
}

object Empty extends IntSet {
    override def value = throw new Error("Пустое множество пусто")
    override def contains(e: Int) = false
    override def include(e: Int) = Element(e, Empty, Empty)
    override def union(e: IntSet): IntSet = e
}