package zeab.aenea.modelsfortest.complexclasses

case class Person(
                   name: String,
                   `class`: String,
                   level: Int,
                   health: Double,
                   soulStone: Option[Boolean],
                   mount: Option[Horse],
                   backpack: List[Item],
                   previousDestinations: Vector[String]
                 )