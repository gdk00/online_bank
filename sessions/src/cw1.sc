import scala.annotation.tailrec

1+1

val i: Int = 1
def f: Int = 1
def F() = 1

val j = f
j
i


def loopI: Int = loopI
def loopB: Boolean = loopB

// call by value
// call by name
def and(x: Boolean, y: => Boolean) = if (x) y else false;
def or(x: Boolean, y: => Boolean) = if (x) true else y;

and(true, false)
and(false, false)
or(true, false)
or(false, false)


def sum(x: Int, y: Int) = x + y

sum(1,4+5)

def first(x: Int, y: =>Int) = x

first(1, loopI)

and(false, loopB)
//and(true, loopB)

var a = 0;
while (a < 3) a = a + 1


def sum(x: Int): Int = if (x > 0) x + sum(x - 1) else 0;
sum(3)
3 + sum(2)
3 + 2 + sum(1)
3 + 2 + 1 + sum(0)
0
3 + 2 + 1
3 + 3
6

def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)


gcd(16, 12)
gcd(12, 4)
gcd(4, 0)
4
//def factorial()

//@tailrec
def factorial(n: Int): Int = if (n == 0) 1 else factorial(n-1) * n


/*
Домашнее задание
  - использовать рекурсию
  - не надо использовать другие конструкции: коллекции, их функции и mapReduce еще предстоит узнать
  - паттерн матчинг тоже не проходили
  - chars: List[Char]: chars.isEmpty chars.head chars.tail
  - функции можно определять в любых блоках
  - любое выражение возвращает результат (в worksheets нет необходимости его печать)
  - форматирование
  - не используем var и циклы

 */
/*

 */