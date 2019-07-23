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
          val node: NodeSeq = loadString(xml) \ symbolName
          coreDeserialize(node, symbolType)
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

  def coreDeserialize(node:Seq[Node], inputType:String)(implicit mirror:Mirror): Any ={
    inputType match {
      case "String" => node.text
      case "Int" => node.text.toInt
      case "Boolean" => node.text.toBoolean
      case n if n.startsWith("Option") =>
        val innerType: String = inputType.drop(7).dropRight(1)
        node.text match {
          case "" => None
          case _ => Some(coreDeserialize(node, innerType))
        }
      case n if n.startsWith("List") =>
        val innerType: String = inputType.drop(5).dropRight(1)
        val ee = node.head.seq.toString()
        val strippedSymbolName: String =
          inputType.split('.').lastOption.getOrElse("").replace("]", "")
        val paramName: String = toCamel(strippedSymbolName)
        val errr = node \ paramName
        val kk =
          errr.map{ss =>
            deserialize(ss.toString(), innerType)
          }.toList
        kk
      case n if n.startsWith("Vector") =>
        val innerType: String = inputType.drop(7).dropRight(1)
        val ee = node.head.seq.toString()
        val strippedSymbolName: String =
          inputType.split('.').lastOption.getOrElse("").replace("]", "")
        val paramName: String = toCamel(strippedSymbolName)
        val errr = node \ paramName
        val kk =
          errr.map{ss =>
            deserialize(ss.toString(), innerType)
          }.toVector
        kk
      case _ =>
        val strippedSymbolName: String =
          inputType.split('.').lastOption.getOrElse("").replace("]", "")
        val paramName: String = toCamel(strippedSymbolName)
        deserialize((node \ paramName).toString(), inputType)
    }
  }

  def toCamel(input: String, delimiter:Char = '.'): String = {
    val split: Array[String] = input.split(delimiter)
    val headLetter: String = split.headOption.getOrElse("").toLowerCase
    val everythingElse: String = split.tail.mkString
    headLetter + everythingElse
  }

}
