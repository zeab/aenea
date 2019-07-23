package zeab.otherrebuild

import zeab.aenea.{Item, Llama, Moose, Pack}
import zeab.otherrebuild.Xboop._
import zeab.aenea.XmlSerializer._
import scala.xml.XML.loadString
object MMMM extends App {

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

  val x = "<llama><name>www</name><friend><moose><name>xx</name><age>7</age></moose></friend></llama>"
  val y = xmlDeserialize[Llama](x)
  println(y)

  //val a = "<pack><items><item><name>sword</name></item><item><name>sword</name></item></items></pack>"
  //val b = xmlDeserialize[Pack](a)
  //println(b)

}
