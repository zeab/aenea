package zeab.otherrebuild

import zeab.aenea.{Llama, Moose}
import zeab.otherrebuild.Xboop._
import scala.xml.XML.loadString
object MMMM extends App {

  val s = loadString("<friend><moose><name>xx</name><age>7</age></moose></friend>")
  val u = (s \ "moose" \ "name").toString()
  println()

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

  println()
  val x = "<llama><name>www</name><friend><moose><name>xx</name><age>7</age></moose></friend></llama>"
  val y = xmlDeserialize[Llama](x)
  println(y)

}
