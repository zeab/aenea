package zeab.otherrebuild

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}
import scala.xml.{Elem, Node, NodeSeq}
import scala.xml.XML.loadString


object Xboop {

  implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)

  def xmlDeserialize[Output](xml: String)(implicit mirror:Mirror, typeTag: TypeTag[Output]): Output ={
    val inputType:String = typeTag.tpe.toString
    val gg = deserialize(xml, inputType)
    println()
    gg.asInstanceOf[Output]
  }

  def deserialize(xml:String, inputType:String)(implicit mirror:Mirror): Any ={
    val outputClass: ClassSymbol = mirror.staticClass(inputType)
    val outputClassValues =
      outputClass.typeSignature.members
        .toList.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => termSymbol }
        .reverse
        .map { symbol =>
          val symbolName: String = symbol.name.toString.trim
          val symbolType: String = symbol.typeSignature.resultType.toString.trim
          val hh = symbolType.split('.').lastOption.getOrElse("")
          val symbolTypeShort: String =
            if (hh == "") ""
            else hh.seq(0).toLower + hh.drop(1)
          val eee = loadString(xml).toString()
          val x = loadString(xml) \ symbol.name.toString.trim
          val rr = x.toString()
          symbol.typeSignature.resultType.toString.trim match {
            case "String" => x.text
            case "Int" => x.text.toInt
            case _ =>
              val ggg = (x \ symbolTypeShort).toString()
              deserialize(ggg.toString, symbol.typeSignature.resultType.toString.trim)
          }
        }
    val classMirror: ClassMirror = mirror.reflectClass(outputClass)
    val constructor: MethodSymbol = outputClass.primaryConstructor.asMethod
    val constructorMirror: MethodMirror = classMirror.reflectConstructor(constructor)
//    Try(constructorMirror.apply(outputClassValues: _*)) match {
//      case Success(instance) => Right(instance)
//      case Failure(ex) => Left(ex)
//    }
    constructorMirror.apply(outputClassValues: _*)
  }
}


//symbol.typeSignature.resultType.toString.trim match {
//  case "String" | "Int" | "Double" =>
//  symbol.name.toString.trim -> symbol.typeSignature.resultType.toString.trim
//  case _ => deserialize(xml, symbol.typeSignature.resultType.toString.trim)
//}
