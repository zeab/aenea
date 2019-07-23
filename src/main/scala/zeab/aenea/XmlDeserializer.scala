package zeab.aenea

/**
  * An automatic case class Xml Deserializer
  *
  * @author Kevin Kosnik-Downs (Zeab)
  * @since 2.12
  */

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}
import scala.xml.{Elem, Node, NodeSeq}
import scala.xml.XML.loadString

object XmlDeserializer {

  implicit class XmlDeserialize(val xml: String) extends AnyVal {
    def fromXml[Output](implicit typeTag: TypeTag[Output]): Either[Throwable, Any] = {
      implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
      deserialize[Output](xml)
    }
  }

  private def deserialize[Output](xml: String)(implicit mirror:Mirror, typeTag: TypeTag[Output]): Either[Throwable, Output] ={
    val inputType:String = typeTag.tpe.toString
    coreDeserialize(xml, inputType) match {
      case Right(obj) => Right(obj.asInstanceOf[Output])
      case Left(ex) => Left(ex)
    }
  }

  private def coreDeserialize(xml:String, inputType:String)(implicit mirror:Mirror): Either[Throwable, Any] ={
    val outputClass: ClassSymbol = mirror.staticClass(inputType)
    val outputClassValues: List[Either[Throwable, Any]] =
      outputClass.typeSignature.members
        .toList.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => termSymbol }
        .reverse
        .map { symbol =>
          val symbolName: String = symbol.name.toString.trim
          val symbolType: String = symbol.typeSignature.resultType.toString.trim
          val node: NodeSeq = loadString(xml) \ symbolName
          convertToValues(node, symbolType)
        }
    flattenEitherValues(outputClassValues) match {
      case Right(values) =>
        val classMirror: ClassMirror = mirror.reflectClass(outputClass)
        val constructor: MethodSymbol = outputClass.primaryConstructor.asMethod
        val constructorMirror: MethodMirror = classMirror.reflectConstructor(constructor)
        Try(constructorMirror.apply(values: _*)) match {
          case Success(instance) => Right(instance)
          case Failure(ex) => Left(ex)
        }
      case Left(ex) => Left(ex)
    }
  }

  private def convertToValues(node:Seq[Node], inputType:String)(implicit mirror:Mirror): Either[Throwable, Any] ={
    inputType match {
      case "String" => Right(node.text)
      case "Int" => returnValueFromTry(Try(node.text.toInt))
      case "Boolean" => returnValueFromTry(Try(node.text.toBoolean))
      case "Double" => returnValueFromTry(Try(node.text.toDouble))
      case "Long" => returnValueFromTry(Try(node.text.toLong))
      case "Short" => returnValueFromTry(Try(node.text.toShort))
      case n if n.startsWith("Option") =>
        val innerType: String = inputType.drop(7).dropRight(1)
        node.text match {
          case "" => Right(None)
          case _ =>
            convertToValues(node, innerType) match {
              case Right(xml) => Right(Some(xml))
              case Left(ex) => Left(ex)
            }
        }
      case n if n.startsWith("List") =>
        val innerType: String = inputType.drop(5).dropRight(1)
        val paramName: String = toCamel(inputType.split('.').lastOption.getOrElse("").replace("]", ""))
        val paramValues: List[Either[Throwable, Any]] =
          (node \ paramName).map{nodeLeaf =>
            coreDeserialize(nodeLeaf.toString(), innerType)
          }.toList
        flattenEitherValues(paramValues) match {
          case Right(value) => Right(value)
          case Left(ex) => Left(ex)
        }
      case n if n.startsWith("Vector") =>
        val innerType: String = inputType.drop(7).dropRight(1)
        val paramName: String = toCamel(inputType.split('.').lastOption.getOrElse("").replace("]", ""))
        val paramValues: List[Either[Throwable, Any]] =
          (node \ paramName).map{nodeLeaf =>
            coreDeserialize(nodeLeaf.toString(), innerType)
          }.toList
        flattenEitherValues(paramValues) match {
          case Right(value) => Right(value)
          case Left(ex) => Left(ex)
        }
      case _ =>
        val paramName: String = toCamel(inputType.split('.').lastOption.getOrElse("").replace("]", ""))
        coreDeserialize((node \ paramName).toString(), inputType)
    }
  }

  private def toCamel(input: String, delimiter:Char = '.'): String = {
    val split: Array[String] = input.split(delimiter)
    val headLetter: String = split.headOption.getOrElse("").toLowerCase
    val everythingElse: String = split.tail.mkString
    headLetter + everythingElse
  }

  private def flattenEitherValues(eitherValues: List[Either[Throwable, Any]]): Either[Throwable, List[Any]] = {
    eitherValues.collectFirst { case Left(f) => f }.toLeft {
      eitherValues.collect { case Right(r) => r }
    }
  }

  private def returnValueFromTry(theTry: Try[Any]): Either[Throwable, Any] =
    theTry match {
      case Success(value) => Right(value)
      case Failure(ex) => Left(ex)
    }

}
