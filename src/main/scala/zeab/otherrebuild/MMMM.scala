package zeab.otherrebuild

import zeab.aenea._
import zeab.aenea.XmlDeserializer._
import zeab.aenea.XmlSerializer._
import zeab.obsolte.{Item, Pack, Saber}

import scala.xml.XML.loadString
object MMMM extends App {

  val saber = Saber(Some(true)).asXml
  println(saber)

  val item = Item("sword")
  val pack = Pack(List(item, item)).asXml

  println(pack)
  val rr: Map[String, Map[String, Any]] =
  Map("llama" ->
    Map(
      "name" -> "String",
      "friend" ->
        Map(
          "moose" ->
            Map(
              "name" -> "String",
              "age" -> "Int"
            )
        )
    )
  )

//  val x = "<llama><name>www</name><friend><moose><name>xx</name><age>7</age></moose></friend></llama>"
//  val y = xmlDeserialize[Llama](x)
//  println(y)

  //val c = "<saber><status>true</status></saber>"
  //val d = xmlDeserialize[Saber](c)
  //println(d)

  val a = "<pack><items><item><name>sword</name></item><item><name>sword</name></item></items></pack>"
  val b = xmlDeserialize[Pack](a)
  println(b)



}
