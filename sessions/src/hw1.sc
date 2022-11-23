import scala.annotation.tailrec
def ??? = throw new NotImplementedError

/*
 * #0 Хвостовая рекурсия
 * Нужно изменить код функции на соответствие хвостовой рекурсии
 * https://ru.wikipedia.org/wiki/%D0%A5%D0%B2%D0%BE%D1%81%D1%82%D0%BE%D0%B2%D0%B0%D1%8F_%D1%80%D0%B5%D0%BA%D1%83%D1%80%D1%81%D0%B8%D1%8F
 * Подсказка: потребуется сделать вспомогательную функцию с дополнительным параметром - "аккумулятором", содержащего результат предварительного расчета
 */

def factorial(n: Int): Int = {
    @tailrec
    def factorialInt(n: Int, acc: Int): Int = if (n < 1) acc else factorialInt(n - 1, n * acc)
    factorialInt(n ,1)
}
factorial(1)
factorial(2)
factorial(3)
factorial(4)

/*
 * #1 Треугольника паскаля
 * https://ru.wikipedia.org/wiki/%D0%A2%D1%80%D0%B5%D1%83%D0%B3%D0%BE%D0%BB%D1%8C%D0%BD%D0%B8%D0%BA_%D0%9F%D0%B0%D1%81%D0%BA%D0%B0%D0%BB%D1%8F
 * Функция должна возвращать значение из треугольника Паскаля по координатам c и r - колонка и строка, соответсвенно, индексация с 0.
*/
def pascal(c: Int, r: Int): Int = {
    if (c == 0 || r == 0 || c == r) 1
    else pascal(c - 1, r -1) + pascal(c, r - 1)
}

/*
 * #2 Балансировка скобок в выражении
 * Требуется написать функцию проверки корректности расстановок скобок в выражении chars.
 * Примеры:
 * "(if (zero? x) max (/ 1 x))" - корректное выражение
 * "(-:-)$-)(-%" - некорректное
*/

def balance(chars: List[Char]): Boolean = {
    def balanceInt(chars: List[Char], acc: Int): Boolean = {
        if (acc < 0 || chars.isEmpty) acc == 0
        else if (chars.head == '(') balanceInt(chars.tail, acc + 1)
        else if (chars.head == ')') balanceInt(chars.tail, acc - 1)
        else balanceInt(chars.tail, acc)
    }
    balanceInt(chars, 0)
}



