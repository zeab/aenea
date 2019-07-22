package zeab.aenea

import zeab.aenea.XmlDeserializer._
import zeab.aenea.XmlSerializer._

object Main extends App {

  val x = Moose("xx", 7)
  val y = Llama("rrr", x).asXml
  println(y)

  //val w = "<moose><name>xx</name><age>7</age></moose>".fromXml[Moose]
  val c = "<llama><name>rrr</name><moose><name>xx</name><age>7</age></moose></llama>".fromXml[Llama]
  println(c)
  //val y = "<moose><name>xx</name></moose>".fromXml[Moose]

  //println(y)

}
