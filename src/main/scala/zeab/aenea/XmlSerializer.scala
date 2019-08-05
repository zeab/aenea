package zeab.aenea

/**
 * An automatic case class to xml serializer
 *
 * @author Kevin Kosnik-Downs (Zeab)
 * @since 2.12
 */

//Imports

import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}

object XmlSerializer {

  implicit class XmlSerialize(val obj: Any) extends AnyVal {
    def asXml: Either[Throwable, String] = {
      implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
      val objSimpleType: String = getObjSimpleTypeName(obj)
      (objSimpleType match {
        case "Vector" | "$colon$colon" | "String" | "Integer" | "Double" | "Boolean" | "Short" | "Long" | "Float" | "Some" | "None$" | "Right" | "Left" | "Null" | "Unit" | "Nil$" | "BigDecimal" | "BigInt" =>
          Left(new Exception(s"Must be a case class at root level cannot serialize : $objSimpleType"))
        case x if x.startsWith("Map") | x.startsWith("Set") => Left(new Exception(s"Must be a case class at root level cannot serialize : $objSimpleType"))
        case _ => serialize(obj)
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

  private def serialize(obj: Any)(implicit mirror: Mirror): Either[Throwable, String] = {
    val objInstanceMirror: InstanceMirror = mirror.reflect(obj)
    val possibleXml: Seq[Either[Throwable, String]] =
      objInstanceMirror.symbol.typeSignature.members.toStream.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => objInstanceMirror.reflectField(termSymbol) }
        .map { fieldMirror: FieldMirror =>
          val mirrorKey: String = fieldMirror.symbol.name.toString.trim
          val mirrorValue: Any = fieldMirror.get
          val mirrorValueType = getObjSimpleTypeName(mirrorValue)
          coreSerialize(mirrorKey, mirrorValue, mirrorValueType)
        }.reverse
    compressEither(possibleXml)
  }

  private def coreSerialize(key: String, value: Any, valueType: String)(implicit mirror: Mirror): Either[Throwable, String] = {
    valueType match {
      case "String" | "Integer" | "Double" | "Boolean" | "Long" | "Short" | "Float" | "BigDecimal" | "BigInt" =>
        if (value == "") Right(s"<$key/>")
        else Right(s"<$key>$value</$key>")
      case "Some" | "None$" =>
        value.asInstanceOf[Option[Any]] match {
          case Some(innerValue) => innerSerialize(key, innerValue)
          case None => Right(s"<$key/>")
        }
      case "$colon$colon" | "Vector" | "Seq" =>
        val possibleXml: Seq[Either[Throwable, String]] =
          value.asInstanceOf[Seq[Any]].map { innerValue: Any => innerSerialize(key, innerValue) }
        if (possibleXml.isEmpty) Right(s"<$key/>")
        else compressEither(possibleXml)
      case _ =>
        serialize(value) match {
          case Right(xml) =>
            if (xml == "") Right(s"<$key/>")
            else Right(s"<$key>$xml</$key>")
          case Left(ex) => Left(ex)
        }
    }
  }

  private def innerSerialize(key: String, innerValue: Any)(implicit mirror: Mirror): Either[Throwable, String] = {
    val innerValueType: String = getObjSimpleTypeName(innerValue)
    innerValueType match {
      case "Seq" | "Vector" | "$colon$colon" | "String" | "Integer" | "Double" | "Boolean" | "Short" | "Long" | "Float" | "Some" | "None$" | "Right" | "Left" | "Null" | "Unit" | "Nil$" | "BigDecimal" | "BigInt" =>
        coreSerialize(key, innerValue, innerValueType)
      case _ => serialize(innerValue) match {
        case Right(xml) =>
          if (xml == "") Right(s"<$key/>")
          else Right(s"<$key>$xml</$key>")
        case Left(ex) => Left(ex)
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
    val headLetter: String = split.headOption.getOrElse(' ').toLower.toString
    val everythingElse: String = split.tail.mkString
    headLetter + everythingElse
  }

}
