
/**
 * Код Хаффмана
 * https://ru.wikipedia.org/wiki/%D0%9A%D0%BE%D0%B4_%D0%A5%D0%B0%D1%84%D1%84%D0%BC%D0%B0%D0%BD%D0%B0
 *
 */
object Huffman {

    /**
     * Код Хаффмана представлен бинарным деревом.
     *
     * Каждый `Leaf` дерева содержит символ, который может быть закодирован.
     * weight - частота использования символа char
     *
     * Узел `Fork` состоит из chars - списка символов, которые он кодирует и weight - суммы весов дочерних узлов
     */
    abstract class CodeTree
    case class Fork(left: CodeTree, right: CodeTree, chars: List[Char], weight: Int) extends CodeTree
    case class Leaf(char: Char, weight: Int) extends CodeTree


    // 1. Базовые методы
    def weight(tree: CodeTree): Int = ??? // tree match ...

    def chars(tree: CodeTree): List[Char] = ??? // tree match ...

    def makeCodeTree(left: CodeTree, right: CodeTree) =
        Fork(left, right, chars(left) ::: chars(right), weight(left) + weight(right))



    // 2. Генерация дерева Хаффмана

    /**
     * Преобразует строку в список символов
     */
    def string2Chars(str: String): List[Char] = str.toList

    /**
     * Данная функция подсчитывает количество уникальных символов в списке. Например
     *
     *   times(List('a', 'b', 'a'))
     *
     * Должна вернуть следующий список (порядок не важен)
     *
     *   List(('a', 2), ('b', 1))
     *
     * Список `List[(Char, Int)]` состоит из пар (кортежей), каждая из которых состоит из символа и числа (вес)
     *
     *   val pair: (Char, Int) = ('c', 1)
     *
     * Для обращения к элементам кортежа можно использовать `_1` and `_2`:
     *
     *   val theChar = pair._1
     *   val theInt  = pair._2
     *
     * Либо pattern match
     *
     *   pair match {
     *     case (theChar, theInt) =>
     *       println("character is: "+ theChar)
     *       println("integer is  : "+ theInt)
     *   }
     */
    def times(chars: List[Char]): List[(Char, Int)] = ???

    /**
     * Возвращает список листьев дерева для частотной таблицы `freqs`
     *
     * Возвращаемый список должен быть отсортировано по возрастанию весов.
     */
    def makeOrderedLeafList(freqs: List[(Char, Int)]): List[Leaf] = ???

    /**
     * Проверяет состоит ли дерево `trees` из одного элемента
     */
    def singleton(trees: List[CodeTree]): Boolean = ???

    /**
     * Параметр `trees`список деревьев отсортированный по возрастанию веса
     *
     * Данная функция берет первые два элемента списка `trees`, комбинирует
     * их в узел `Fork` и возвращает список с остальными элементами и полученным узлом,
     * добавленного на позицию, сохраняющую упорядоченность списка по весу.
     *
     * Если `trees` содержит менее двух элементов, возвращается `trees` без изменения
     */
    def combine(trees: List[CodeTree]): List[CodeTree] = ???

    /**
     *  Эта функция вызывается следующим образом:
     *
     *   until(singleton, combine)(trees)
     *
     *
     * где `trees` типа `List[CodeTree]`, `singleton` and `combine` ссылаются на соответствующие функции,
     * определенные выше.
     *
     * В данном вызове `until` должна вызывать две этих функции
     * пока список с элементами дерева не будет содержать одного элемента
     *
     * Подсказка: перед реализацией
     *  - начните с определением типов параметрам, согласно примеру вызова. Также определите тип возвращаемого значения
     *  - подберите соответствующие название параметров.
     */
    def until(xxx: ???, yyy: ???)(zzz: ???): ??? = ???

    /**
     * Данная функция создает дерево `CodeTree`, оптимизированное для кодировки `chars`.
     *
     */
    def createCodeTree(chars: List[Char]): CodeTree = ???


    // 3: Расшифрование

    type Bit = Int

    /**
     * Кодирует последовательность `bits` с использоваем дерева `tree`
     */
    def decode(tree: CodeTree, bits: List[Bit]): List[Char] = ???

    val frenchCode: CodeTree = Fork(Fork(Fork(Leaf('s',121895),Fork(Leaf('d',56269),Fork(Fork(Fork(Leaf('x',5928),Leaf('j',8351),List('x','j'),14279),Leaf('f',16351),List('x','j','f'),30630),Fork(Fork(Fork(Fork(Leaf('z',2093),Fork(Leaf('k',745),Leaf('w',1747),List('k','w'),2492),List('z','k','w'),4585),Leaf('y',4725),List('z','k','w','y'),9310),Leaf('h',11298),List('z','k','w','y','h'),20608),Leaf('q',20889),List('z','k','w','y','h','q'),41497),List('x','j','f','z','k','w','y','h','q'),72127),List('d','x','j','f','z','k','w','y','h','q'),128396),List('s','d','x','j','f','z','k','w','y','h','q'),250291),Fork(Fork(Leaf('o',82762),Leaf('l',83668),List('o','l'),166430),Fork(Fork(Leaf('m',45521),Leaf('p',46335),List('m','p'),91856),Leaf('u',96785),List('m','p','u'),188641),List('o','l','m','p','u'),355071),List('s','d','x','j','f','z','k','w','y','h','q','o','l','m','p','u'),605362),Fork(Fork(Fork(Leaf('r',100500),Fork(Leaf('c',50003),Fork(Leaf('v',24975),Fork(Leaf('g',13288),Leaf('b',13822),List('g','b'),27110),List('v','g','b'),52085),List('c','v','g','b'),102088),List('r','c','v','g','b'),202588),Fork(Leaf('n',108812),Leaf('t',111103),List('n','t'),219915),List('r','c','v','g','b','n','t'),422503),Fork(Leaf('e',225947),Fork(Leaf('i',115465),Leaf('a',117110),List('i','a'),232575),List('e','i','a'),458522),List('r','c','v','g','b','n','t','e','i','a'),881025),List('s','d','x','j','f','z','k','w','y','h','q','o','l','m','p','u','r','c','v','g','b','n','t','e','i','a'),1486387)

    /**
     * Данную последовательность битов надо декодировать с помощью кода `frenchCode`
     */
    val secret: List[Bit] = List(1, 0, 0, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0)

    /**
     * Возращает декодированную последовательность secret
     */
    def decodedSecret: List[Char] = decode(frenchCode, secret)


    // 4a: Простое шифрование

    /**
     * Кодирует `text` с использованием `tree`
     */
    def encode(tree: CodeTree)(text: List[Char]): List[Bit] = ???

    // 4b: Шифрование с использованием таблицы кодов

    type CodeTable = List[(Char, List[Bit])]

    def codeBits(table: CodeTable)(char: Char): List[Bit] = ???

    def convert(tree: CodeTree): CodeTable = ???

    def mergeCodeTables(a: CodeTable, b: CodeTable): CodeTable = ???

    def quickEncode(tree: CodeTree)(text: List[Char]): List[Bit] = ???
}