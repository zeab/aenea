package zeab.aenea

object Main extends App {

  val t = Map("loyaltyMember" -> List(Item("shield", "hand"), Item("shield", "hand")))


  val a = Map("loyaltyMember" -> Item("aaa", "ssss") )

  val u = Moose(Item("sword", "hand"), Item("shield", "hand"))

  val h = Item("sword", "hand")

  val p =
    Person(
      "bob",
      List(Item("sword", "hand"), Item("shield", "hand"), Item("horse", "mount")),
      100,
      List("moose", "llama")
    )

  val g = BackPack(
    Item("shield", "hand"),
    Item("horse", "mount")
  )

  val x = Items(List(Item("sword", "hand")))//List(Item("sword", "hand"), Item("shield", "hand"), Item("horse", "mount"))
  val y = XmlSeri.xmlSerialize(g)

  println(y)

  //val i = u.isInstanceOf[AnyVal]
  //val o = u.isInstanceOf[Object]
//  println()
//
//
//  val x = List(Item("sword", "hand"), Item("sword", "hand"))
//  val y = XmlSerialize.xmlSerialize[String](8)
//
//  val a = "<myList><myListOption/><myListOption>xx</myListOption><myListOption>tt</myListOption></myList>"
//  val b = XmlDeserialize.xmlDeserialize[MyList](a)
//
//  println(y)

}
