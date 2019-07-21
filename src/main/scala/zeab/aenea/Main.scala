package zeab.aenea

import zeab.aenea.XmlDeserializer._

object Main extends App {

  val x = "moose".fromXml[Moose]

  println(x)

}
