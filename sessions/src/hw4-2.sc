
// Натуральные числа
abstract class Nat{
    // является ли нулем
    def isZero: Boolean
    // возвращает предыдущее натуральное число
    def predecessor: Nat
    // возвращает следущее натуральное число
    def successor: Nat = Succ(this)
    // возвращает результат сложения с that
    def +(that: Nat): Nat
    // возвращает результат вычитания that
    def -(that: Nat):Nat
}

object Zero extends Nat{
    // является ли нулем
    override def isZero: Boolean = true

    // возвращает предыдущее натуральное число
    override def predecessor: Nat = throw new Error()

    // возвращает результат сложения с that
    override def +(that: Nat): Nat = ???

    // возвращает результат вычитания that
    override def -(that: Nat): Nat = ???
}

case class Succ(n: Nat) extends Nat{
    // является ли нулем
    override def isZero: Boolean = false

    // возвращает предыдущее натуральное число
    override def predecessor: Nat = n

    // возвращает результат сложения с that
    override def +(that: Nat): Nat = this + that

    // возвращает результат вычитания that
    override def -(that: Nat): Nat = ???
}

val _1 = Succ(Zero)
val _2 = Succ(Succ(Zero))
// _2 + _1 = Succ(Succ(Zero)) + Succ(Zero) = Succ(Succ(Succ(Zero)))

// a + b = (a - 1) + (b + 1)