abstract class Логический {
    def ifThenElse[A](t: => A, e: => A): A
    def unary_! = ifThenElse(ложь, правда)
    def ==(x: => Логический): Логический = ifThenElse(x, !x)
    def и(y: Логический) = ifThenElse(y, ложь)
    def или(y: Логический) = ifThenElse(правда, y)
    def !=(x: => Логический): Логический = ifThenElse(!x, x)
}

object правда extends Логический{
    def ifThenElse[A](t: => A, e: => A): A = t

    override def toString: String = "правда"
}

object ложь extends Логический {
    def ifThenElse[A](t: => A, e: => A): A = e

    override def toString: String = "ложь"
}

object bool extends App {
        println(правда и правда == правда)
        println(правда или ложь == правда)
        println((правда или ложь) == правда)
        println(ложь и правда)
        println(ложь и ложь)

//    правда и ложь == правда
//    ложь и правда
//    ложь и ложь
//    правда или правда
//    правда или ложь
//    ложь или правда
//    ложь или ложь
//
//    (правда и ложь) == ложь


}

