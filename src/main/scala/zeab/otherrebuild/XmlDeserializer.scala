package zeab.otherrebuild

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}
import scala.xml.{Elem, Node, NodeSeq}
import scala.xml.XML.loadString

object XmlDeserializer {

  implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)

  def xmlDeserialize[Output](input: String)(implicit mirror:Mirror, typeTag: TypeTag[Output]): Either[Throwable, Output] ={
    val inputType:String = typeTag.tpe.toString
    val gg = deserialize(inputType)
    println()
    ???
  }

  def deserialize(inputType:String): Either[Throwable, Map[String, Map[String, String]]] ={
    val outputClass: ClassSymbol = mirror.staticClass(inputType)
    val reflectedValues =
      outputClass.typeSignature.members
        .toStream.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => termSymbol }
        .map { symbol =>
          val ww =
            symbol.typeSignature.resultType.toString.trim match {
              case "String" | "Int" => symbol.name.toString.trim -> symbol.typeSignature.resultType.toString.trim
              case _ =>
                val tttt = symbol.typeSignature.resultType.toString.trim
                println()
                deserialize(symbol.typeSignature.resultType.toString.trim)
            }

          symbol.name.toString.trim -> symbol.typeSignature.resultType.toString.trim
        }.toMap
    val tt = Map(inputType -> reflectedValues)
    val g: Either[Throwable, Map[String, Map[String, String]]] =
    if (tt.isEmpty) Left(new Exception("o no!"))
    else Right(tt)
    g
  }

}
