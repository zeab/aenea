
import zeab.aenea.{Moose, MyNullClass}
import zeab.aenea.XmlSerializer._


object Main extends App {

  val y = Moose(null).asXml(Map("isnullaccepted" -> "true"))

  val x = MyNullClass(null)

  println()

}
