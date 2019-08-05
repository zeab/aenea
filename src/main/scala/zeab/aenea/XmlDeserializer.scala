package zeab.aenea

/**
 * An automatic case class xml deserializer
 *
 * @author Kevin Kosnik-Downs (Zeab)
 * @since 2.12
 */

//Imports

import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}
import scala.xml.XML.loadString
import scala.xml.{Node, NodeSeq}

object XmlDeserializer {

  implicit class XmlDeserialize(val xml: String) extends AnyVal {
    def fromXml[Output](implicit typeTag: TypeTag[Output]): Either[Throwable, Output] = {
      Try(loadString(xml)) match {
        case Success(actualXml) =>
          xml match {
            case "" => Left(new Exception("xml input cannot be blank"))
            case _ =>
              typeTag.tpe.toString match {
                case "String" => Right(xml.asInstanceOf[Output])
                case "BigDecimal" | "BigInt" | "Int" | "Double" | "Float" | "Double" | "Short" | "Long" | "Right" | "Left" => Left(new Exception("Must supply a case class to deserializer"))
                case x if x.startsWith("Map") | x.startsWith("Vector") | x.startsWith("Set") | x.startsWith("List") => Left(new Exception("Must supply a case class to deserializer"))
                case outputType: String =>
                  implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
                  deserialize(actualXml.seq, outputType) match {
                    case Right(output) => Right(output.asInstanceOf[Output])
                    case Left(ex) => Left(ex)
                  }
              }
          }
        case Failure(ex) => Left(ex)
      }
    }
  }

  private def deserialize(xml: Seq[Node], outputType: String)(implicit mirror: Mirror): Either[Throwable, Any] = {
    val outputClass: ClassSymbol = mirror.staticClass(outputType)
    val outputClassValues: List[Either[Throwable, Any]] =
      outputClass.typeSignature.members
        .toList.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => termSymbol: TermSymbol }
        .reverse
        .map { symbol: TermSymbol =>
          val symbolName: String = symbol.name.toString.trim
          val symbolType: String = symbol.typeSignature.resultType.toString.trim
          val node: NodeSeq = xml \ symbolName
          coreDeserialize(node, symbolType)
        }
    compressEither(outputClassValues) match {
      case Right(values) =>
        val classMirror: ClassMirror = mirror.reflectClass(outputClass)
        val constructor: MethodSymbol = outputClass.primaryConstructor.asMethod
        val constructorMirror: MethodMirror = classMirror.reflectConstructor(constructor)
        Try(constructorMirror.apply(values: _*)) match {
          case Success(instance) => Right(instance)
          case Failure(ex) =>
            //TODO Battle harden this exception so that it actually tells me what failed in a useful way...
            Left(new Exception(s"${ex.toString} on type : $outputClass : $outputType"))
        }
      case Left(ex) => Left(ex)
    }
  }

  private def coreDeserialize(xml: Seq[Node], outputType: String)(implicit mirror: Mirror): Either[Throwable, Any] = {
    outputType match {
      case "Unit" | "Null" => Left(new Exception(s"unsupported type: $outputType"))
      case "String" => Right(xml.text)
      case "BigDecimal" => returnValueFromTry(Try(BigDecimal(xml.text.toDouble)))
      case "BigInt" => returnValueFromTry(Try(BigInt(xml.text.toInt)))
      case "Int" => returnValueFromTry(Try(xml.text.toInt))
      case "Boolean" => returnValueFromTry(Try(xml.text.toBoolean))
      case "Double" => returnValueFromTry(Try(xml.text.toDouble))
      case "Float" => returnValueFromTry(Try(xml.text.toFloat))
      case "Long" => returnValueFromTry(Try(xml.text.toLong))
      case "Short" => returnValueFromTry(Try(xml.text.toShort))
      case x if x.startsWith("Option") =>
        val innerType: String = outputType.drop(7).dropRight(1)
        //TODO Make sure this is right... taking the head i mean... I feel that by the time i get here I should be dealing with only 1 node anyways... but just make sure
        xml.headOption match {
          case Some(node) =>
            node.text match {
              case "" => Right(None)
              case _ =>
                innerDeserialize(node, innerType) match {
                  case Right(value) => Right(Some(value))
                  case Left(ex) => Left(ex)
                }
            }
          case None => Left(new Exception("error dealing with options... some how... real error TBD"))
        }
      case x if x.startsWith("Seq") =>
        val innerType: String = outputType.drop(4).dropRight(1)
        if (xml.text == "") Right(Seq.empty)
        else compressEither(xml.map { node: Node => innerDeserialize(node, innerType) })
      case x if x.startsWith("List") =>
        val innerType: String = outputType.drop(5).dropRight(1)
        if (xml.text == "") Right(List.empty)
        else compressEither(xml.map { node: Node => innerDeserialize(node, innerType) })
      case x if x.startsWith("Vector") =>
        val innerType: String = outputType.drop(7).dropRight(1)
        if (xml.text == "") Right(Vector.empty)
        else
          compressEither(xml.map { node => innerDeserialize(node, innerType) }) match {
            case Right(value) => Right(value.toVector)
            case Left(ex) => Left(ex)
          }
      case _ => deserialize(xml, outputType)
    }
  }

  private def innerDeserialize(xml: Seq[Node], outputType: String)(implicit mirror: Mirror): Either[Throwable, Any] =
    outputType match {
      case x if x.startsWith("Option") | x.startsWith("Vector") | x.startsWith("List") | x.startsWith("Seq") => coreDeserialize(xml, outputType)
      case "String" | "Int" | "Double" | "Unit" | "Null" | "BigDecimal" | "BigInt" | "Boolean" | "Float" | "Long" | "Short" =>
        coreDeserialize(xml, outputType)
      case _ => deserialize(xml, outputType)
    }

  private def compressEither(eitherValues: Seq[Either[Throwable, Any]]): Either[Throwable, Seq[Any]] =
    eitherValues.collectFirst { case Left(l) => l }.toLeft {
      eitherValues.collect { case Right(r) => r }
    }

  private def returnValueFromTry(theTry: Try[Any]): Either[Throwable, Any] =
    theTry match {
      case Success(tryResult) => Right(tryResult)
      case Failure(ex) => Left(ex)
    }

}
