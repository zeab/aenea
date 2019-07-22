package zeab.aenea

import zeab.aenea.XmlDeserializer._
import zeab.aenea.XmlSerializer._

import scala.xml.XML.loadString

object Main extends App {

  val x = Moose("xx", 7).asXml

  println(x)

  val w = "<moose><name>xx</name><age>7</age></moose>".fromXml[Moose]

  println(w)
  //val y = "<moose><name>xx</name></moose>".fromXml[Moose]

  //println(y)

}
