package zeab.rebuild

import zeab.aenea.XmlSerializer._
import zeab.aenea.{Llama, Moose}
import zeab.rebuild.XmlDeserializer._

object Ma extends App {

  val moose = Moose("xx", 7)
  val llama = Llama("www", moose).asXml

  val w = "<moose><name>xx</name><age>7</age></moose>".fromXml[Moose]
  //val c = "<llama><name>www</name><friend><moose><name>xx</name><age>7</age></moose></friend></llama>".fromXml[Llama]
  println(w)

}
