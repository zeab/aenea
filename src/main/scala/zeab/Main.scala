package zeab

import zeab.aenea.XmlSerializer._
import zeab.models.{Donkey, Koala}

object Main extends App {

  val x = Donkey(Vector.empty).asXml

  println(x)

}
