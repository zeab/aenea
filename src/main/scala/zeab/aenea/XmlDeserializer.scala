package zeab.aenea

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}
import scala.xml.Elem
import scala.xml.XML.loadString

object XmlDeserializer {

  implicit class XmlDeserialize(input: String) {
    implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
    def fromXml[T]: Either[Throwable, T] =
      Try(loadString(input)) match {
        case Success(xml) => deserialize[T](xml)
        case Failure(ex) => Left(ex)
      }
  }

  private def deserialize[T](xml: Elem)(implicit mirror: Mirror): Either[Throwable, T] = {
    ???
  }

}
