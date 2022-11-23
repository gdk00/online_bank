/*

Что может пригодиться:
0. https://www.codewars.com/kata/550f22f4d758534c1100025a/scala
1. Проверка разнонаправленных направлений
```
val oppositeDirs = Map(
    "NORTH" -> "SOUTH",
    "EAST" -> "WEST",
    "SOUTH" -> "NORTH",
    "WEST" -> "EAST"
)
def isOpposite(one: String, other: String): Boolean = oppositeDirs(one).equals(other)

def dirReduc(arr: Array[String]): Array[String] = ???

```

2. Обращение с массивом:
```
val arr = Array("NORTH", "SOUTH", "SOUTH", "EAST", "WEST", "NORTH", "WEST")
arr.head  // первый элемент
arr.tail  // все кроме первого
arr.last  // последний элемент
arr.init  // все кроме последнего
val empty = Array() // создание пустого
val newarr = arr :+ "EAST" // добавление элемента в конец массива
val newarr2 = "WEST" +: arr // добавление элемента в начало массива
```


3. Задание упрощено:
dirReduc(Array("NORTH", "EAST", "SOUTH", "WEST", "WEST")) == Array("NORTH", "EAST", "SOUTH", "WEST", "WEST") // а не Array(WEST) как могло бы быть

 */


val oppositeDirs = Map(
    "NORTH" -> "SOUTH",
    "EAST" -> "WEST",
    "SOUTH" -> "NORTH",
    "WEST" -> "EAST"
)
def isOpposite(one: String, other: String): Boolean = oppositeDirs(one).equals(other)

def dirReduc(arr: Array[String]): Array[String] = ???


