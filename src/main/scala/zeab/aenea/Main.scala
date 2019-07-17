package zeab.aenea

object Main extends App {

  val x = MyList(List(None, Some("xx"), Some("tt")) )
  val y = XmlSerialize.xmlSerialize[String](x)

  val a = "<myList><myListOption/><myListOption>xx</myListOption><myListOption>tt</myListOption></myList>"
  val b = XmlDeserialize.xmlDeserialize[MyList](a)

  println(b)

}
