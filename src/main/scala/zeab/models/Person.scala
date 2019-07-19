package zeab.models

case class Person(
                   name: String,
                   backpack: List[Item],
                   health: Int,
                   nickNames: List[String]
                 )
