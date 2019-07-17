package zeab.aenea

object Main extends App {

  val x = Person("bob", List(Item("sword", "hand"), Item("shield", "hand")))
  val y = XmlSerialize.xmlSerialize(x)

  val a = "<person><name>bob</name><backpack><item><name>sword</name><slot>hand</slot></item></backpack><backpack><item><name>shield</name><slot>hand</slot></item></backpack></person>"
  val b = XmlDeserialize.xmlDeserialize[Person](a)

  println(b)
  println(y)


}
