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

  implicit class XmlDeserializeFromString(val xml: String) {
    def fromXml[Output](options: Map[String, String] = Map.empty)(implicit typeTag: TypeTag[Output]): Either[Throwable, Output] =
      handleDeserialize[Output](xml, options)
  }

  implicit class XmlDeserializeFromEither(val possibleXml: Either[Throwable, String]) {
    def fromXml[Output](options: Map[String, String] = Map.empty)(implicit typeTag: TypeTag[Output]): Either[Throwable, Output] =
      possibleXml match {
        case Right(xml) => handleDeserialize[Output](xml, options)
        case Left(ex) => Left(ex)
      }
  }

  private def handleDeserialize[Output](xml: String, options: Map[String, String])(implicit typeTag: TypeTag[Output]): Either[Throwable, Output] =
    Try(loadString(xml)) match {
      case Success(actualXml) =>
        typeTag.tpe.toString match {
          case "String" => Right(xml.asInstanceOf[Output])
          case "BigDecimal" | "BigInt" | "Int" | "Double" | "Float" | "Double" | "Short" | "Long" | "Right" | "Left" => Left(new Exception(s"Must supply a case class to deserializer ${typeTag.tpe} is not valid"))
          case tag if tag.startsWith("Map") | tag.startsWith("Vector") | tag.startsWith("Set") | tag.startsWith("List") => Left(new Exception(s"Must supply a case class to deserializer ${typeTag.tpe} is not valid"))
          case outputType: String =>
            implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
            deserialize(actualXml.seq, outputType, options) match {
              case Right(output) => Right(output.asInstanceOf[Output])
              case Left(ex) => Left(ex)
            }
        }
      case Failure(ex) => Left(ex)
    }

  private def deserialize(xml: Seq[Node], outputType: String, options: Map[String, String])(implicit mirror: Mirror): Either[Throwable, Any] = {
    val outputClass: ClassSymbol = mirror.staticClass(outputType)
    val outputClassValues: List[Either[Throwable, Any]] =
      outputClass.typeSignature.members
        .toList.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => termSymbol: TermSymbol }
        .reverse
        .map ( symbol => coreDeserialize(xml \ symbol.name.toString.trim, symbol.typeSignature.resultType.toString.trim, options) )
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

  private def coreDeserialize(xml: Seq[Node], outputType: String, options: Map[String, String])(implicit mirror: Mirror): Either[Throwable, Any] = {
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
      case tag if tag.startsWith("Option") =>
        val innerType: String = outputType.drop(7).dropRight(1)
        //TODO Make sure this is right... taking the head i mean... I feel that by the time i get here I should be dealing with only 1 node anyways... but just make sure
        xml.headOption match {
          case Some(node) =>
            node.text match {
              case "" => Right(None)
              case _ =>
                innerDeserialize(node, innerType, options) match {
                  case Right(value) => Right(Some(value))
                  case Left(ex) => Left(ex)
                }
            }
          case None => Left(new Exception("error dealing with options... some how... real error TBD"))
        }
      case tag if tag.startsWith("Seq") =>
        val innerType: String = outputType.drop(4).dropRight(1)
        if (xml.text == "") Right(Seq.empty)
        else compressEither(xml.map { node: Node => innerDeserialize(node, innerType, options) })
      case tag if tag.startsWith("List") =>
        val innerType: String = outputType.drop(5).dropRight(1)
        if (xml.text == "") Right(List.empty)
        else compressEither(xml.map { node: Node => innerDeserialize(node, innerType, options) })
      case tag if tag.startsWith("Vector") =>
        if (options.find(_._1.toLowerCase == "isvectorwrapped").map(_._2).getOrElse("false").toBoolean){
          ???
        }
        else {
          val innerType: String = outputType.drop(7).dropRight(1)
          if (xml.text == "") Right(Vector.empty)
          else
            compressEither(xml.map { node: Node => innerDeserialize(node, innerType, options) }) match {
              case Right(value) => Right(value.toVector)
              case Left(ex) => Left(ex)
            }
        }
      case _ => deserialize(xml, outputType, options)
    }
  }

  private def innerDeserialize(xml: Seq[Node], outputType: String, options: Map[String, String])(implicit mirror: Mirror): Either[Throwable, Any] =
    outputType match {
      case tag if tag.startsWith("Option") | tag.startsWith("Vector") | tag.startsWith("List") | tag.startsWith("Seq") => coreDeserialize(xml, outputType, options)
      case "String" | "Int" | "Double" | "Unit" | "Null" | "BigDecimal" | "BigInt" | "Boolean" | "Float" | "Long" | "Short" =>
        coreDeserialize(xml, outputType, options)
      case _ => deserialize(xml, outputType, options)
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
