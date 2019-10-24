package zeab.aenea.modelsfortest.complexclasses

case class Backpack(
                     owner: String,
                     currentWeight:Int,
                     items: Vector[Item]
                   )
