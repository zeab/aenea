package zeab.aenea

/**
 * An automatic case class to xml serializer
 *
 * @author Kevin Kosnik-Downs (Zeab)
 * @since 2.12
 */

//Imports
import zeab.aenea.customexceptions.{InvalidCaseClassException, SerializationException}
//Scala
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}

object XmlSerializer {

  implicit class XmlSerialize(val obj: Any) {
    def asXml(options:Map[String, String] = Map.empty): Either[Throwable, String] = {
      implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
      val objSimpleType: String = getObjSimpleTypeName(obj)
      (objSimpleType match {
        case "String" => Right(obj.toString)
        case "Vector" | "$colon$colon" | "Integer" | "Double" | "Boolean" | "Short" | "Long" | "Float" | "Some" | "None$" | "Right" | "Left" | "Null" | "Unit" | "BoxedUnit" | "Nil$" | "BigDecimal" | "BigInt" =>
          Left(SerializationException("root", obj, InvalidCaseClassException(objSimpleType)))
        case tag if tag.startsWith("Map") | tag.startsWith("Set") | tag.startsWith("Seq") =>
          Left(SerializationException("root", obj, InvalidCaseClassException(objSimpleType)))
        case _ => serialize(obj, options)
      }) match {
        case Right(innerXml) =>
          val camelObjName: String = toCamel(objSimpleType)
          innerXml match {
            case "" => Right(s"<$camelObjName/>")
            case xml: String => Right(s"<$camelObjName>$xml</$camelObjName>")
          }
        case Left(ex) => Left(ex)
      }
    }
  }

  implicit class XmlSerializeNull(val obj: Null){
    def asXml(options:Map[String, String] = Map.empty): Either[Throwable, String] =
      Left(SerializationException("root", "null", InvalidCaseClassException("null")))
  }

  private def serialize(obj: Any, options: Map[String, String])(implicit mirror: Mirror): Either[Throwable, String] = {
    val objInstanceMirror: InstanceMirror = mirror.reflect(obj)
    val possibleXml: Seq[Either[Throwable, String]] =
      objInstanceMirror.symbol.typeSignature.members.toStream.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => objInstanceMirror.reflectField(termSymbol) }
        .map { fieldMirror: FieldMirror =>
          val mirrorKey: String = fieldMirror.symbol.name.toString.trim
          val mirrorValue: Any = fieldMirror.get
          coreSerialize(mirrorKey, mirrorValue, getObjSimpleTypeName(mirrorValue), options)
        }.reverse
    compressEither(possibleXml)
  }

  private def coreSerialize(key: String, value: Any, valueType: String, options: Map[String, String])(implicit mirror: Mirror): Either[Throwable, String] = {
    valueType match {
      case "Null" =>
        if (options.find(_._1.toLowerCase == "isnullaccepted").map(_._2).getOrElse("false").toBoolean) Right("")
        else Left(SerializationException(key, "null", InvalidCaseClassException("null")))
      case "String" | "Integer" | "Double" | "Boolean" | "Long" | "Short" | "Float" | "BigDecimal" | "BigInt" =>
        if (value == "") Right(s"<$key/>")
        else Right(s"<$key>$value</$key>")
      case "Some" | "None$" =>
        Try(value.asInstanceOf[Option[Any]]) match {
          case Success(casedValue) =>
            casedValue match {
              case Some(innerValue) => innerSerialize(key, innerValue, options)
              case None => Right(s"<$key/>")
            }
          case Failure(ex) => Left(SerializationException(key, value, ex))
        }
      case "$colon$colon" | "Seq" =>
        Try(value.asInstanceOf[Seq[Any]]) match {
          case Success(casedValue) =>
            val possibleXml: Seq[Either[Throwable, String]] =
              casedValue.map { innerValue: Any => innerSerialize(key, innerValue, options) }
            if (possibleXml.isEmpty) Right(s"<$key/>")
            else compressEither(possibleXml)
          case Failure(ex) => Left(SerializationException(key, value, ex))
        }
      case "Vector" =>
        if (options.find(_._1.toLowerCase == "isvectorwrapped").map(_._2).getOrElse("false").toBoolean)
          Try(value.asInstanceOf[Seq[Any]]) match {
            case Success(casedValue) =>
              val possibleXml: Seq[Either[Throwable, String]] =
                casedValue.map { innerValue: Any =>
                  innerValue.getClass.getSimpleName match {
                    case "String" | "Integer" | "Double" | "Boolean" | "Long" | "Short" | "Float" | "BigDecimal" | "BigInt" =>
                      innerSerialize(key, innerValue, options)
                    case _ => innerSerialize(toCamel(innerValue.getClass.getSimpleName), innerValue, options)
                  }
                }
              if (possibleXml.isEmpty) Right(s"<$key/>")
              else compressEither(possibleXml) match {
                case Right(xml) =>
                  if (xml.isEmpty) Right(s"<$key/>")
                  else Right(s"<$key>$xml</$key>")
                case Left(ex) => Left(SerializationException(key, value, ex))
              }
            case Failure(ex) => Left(SerializationException(key, value, ex))
          }
        else
          Try(value.asInstanceOf[Seq[Any]]) match {
            case Success(casedValue) =>
              val possibleXml: Seq[Either[Throwable, String]] =
                casedValue.map ( innerValue => innerSerialize(key, innerValue, options) )
              if (possibleXml.isEmpty) Right(s"<$key/>")
              else compressEither(possibleXml)
            case Failure(ex) => Left(SerializationException(key, value, ex))
          }
      case _ =>
        serialize(value, options) match {
          case Right(xml) =>
            if (xml == "") Right(s"<$key/>")
            else Right(s"<$key>$xml</$key>")
          case Left(ex) => Left(SerializationException(key, value, ex))
        }
    }
  }

  private def innerSerialize(key: String, innerValue: Any, options: Map[String, String])(implicit mirror: Mirror): Either[Throwable, String] = {
    val innerValueType: String = getObjSimpleTypeName(innerValue)
    innerValueType match {
      case "Seq" | "Vector" | "$colon$colon" | "String" | "Integer" | "Double" | "Boolean" | "Short" | "Long" | "Float" | "Some" | "None$" | "Right" | "Left" | "Null" | "Unit" | "Nil$" | "BigDecimal" | "BigInt" =>
        coreSerialize(key, innerValue, innerValueType, options)
      case _ =>
        serialize(innerValue, options) match {
          case Right(xml) =>
            if (xml == "") Right(s"<$key/>")
            else Right(s"<$key>$xml</$key>")
          case Left(ex) => Left(SerializationException(key, innerValue, ex))
        }
    }
  }

  private def getObjSimpleTypeName(obj: Any): String =
    Try(obj.getClass.getSimpleName) match {
      case Success(name) => name
      case Failure(_) => "Null"
    }

  private def compressEither(eitherValues: Seq[Either[Throwable, String]]): Either[Throwable, String] =
    eitherValues.collectFirst { case Left(l) => l }.toLeft {
      eitherValues.collect { case Right(r) => r }.mkString
    }

  private def toCamel(input: String): String = {
    val split: Array[Char] = input.toArray
    split.headOption.getOrElse(' ').toLower.toString + split.tail.mkString
  }

}
