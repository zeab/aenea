package zeab.rebuild

/**
  * An automatic case class Xml Serializer
  *
  * @author Kevin Kosnik-Downs (Zeab)
  * @since 2.12
  */

//Imports
import zeab.rebuild.RunTimeMirror._
//Scala
import scala.reflect.runtime.universe._

object XmlSerializer extends AeneaCore {

  implicit class XmlSerialize(obj: Any) {
    def asXml: Either[Throwable, String] =
      serialize(obj)
  }

  def serialize(obj: Any)(implicit mirror: Mirror): Either[Throwable, String] = {
    val objName: String = getObjName(obj)
    objName match {
      case "String" | "Integer" | "Double" | "Boolean" | "Some" | "None$" | "Right" | "Left" | "Null" =>
        Left(new Exception("cannot serialize on a primitive"))
      case "Vector" =>
        Left(new Exception("not implemented"))
      case x if x.contains("Map") =>
        val (paramKeys, paramValues) =
          obj.asInstanceOf[Map[String, Any]].map { param =>
            val (paramKey, paramValue): (String, Any) = param
            (paramKey, serialize(paramValue))
          }.unzip
        flattenEitherValuesAndRightString(paramValues.toList) match {
          case Right(xml) =>
            val key: String = paramKeys.headOption.getOrElse("")
            Right(s"<$key>$xml</$key>")
          case Left(ex) => Left(ex)
        }
      case "$colon$colon" =>
        val params: List[Either[Throwable, String]] =
          obj.asInstanceOf[List[Any]].map { param => serialize(param) }
        flattenEitherValuesAndRightString(params)
      case _ =>
        val objInstanceMirror: InstanceMirror = mirror.reflect(obj)
        val possibleXml: List[Either[Throwable, String]] =
          objInstanceMirror.symbol.typeSignature.members.toStream.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => objInstanceMirror.reflectField(termSymbol) }
            .map { fieldMirror =>
              val mirrorKey: String = fieldMirror.symbol.name.toString.trim
              val mirrorValue: Any = fieldMirror.get
              coreSerialize(mirrorKey, mirrorValue)
            }.reverse.toList
        flattenEitherValuesAndRightString(possibleXml) match {
          case Right(xml) =>
            val camelObjName: String = objName.seq(0).toLower + objName.drop(1)
            Right(s"<$camelObjName>$xml</$camelObjName>")
          case Left(ex) => Left(ex)
        }
    }
  }

  def coreSerialize(mirrorKey: String, mirrorValue: Any)(implicit mirror: Mirror): Either[Throwable, String] = {
    val mirrorValueType: String = getObjName(mirrorValue)
    mirrorValueType match {
      case "String" | "Integer" | "Double" | "Boolean" | "Long" | "Short" =>
        mirrorValue.toString match {
          case "" => Right(s"<$mirrorKey/>")
          case _ => Right(s"<$mirrorKey>$mirrorValue</$mirrorKey>")
        }
      case "Some" | "None$" =>
        mirrorValue.asInstanceOf[Option[Any]] match {
          case Some(optionValue) =>
            coreSerialize(mirrorKey, optionValue)
          case None => Right(s"<$mirrorKey/>")
        }
      case "Right" | "Left" =>
        Left(new Exception("unsupported Left|Right"))
      case "Vector" =>
        val params: List[Either[Throwable, String]] =
          mirrorValue.asInstanceOf[Vector[Any]].map { param =>
            val paramType: String = getObjName(param)
            paramType match {
              case "String" | "Integer" | "Double" | "Boolean" | "Some" | "None$" | "Right" | "Left" | "Null" => coreSerialize(mirrorKey, param)
              case _ => serialize(param)
            }
          }.toList
        flattenEitherValuesAndRightString(params) match {
          case Right(xml) =>
            xml match {
              case "" => Right(s"<$mirrorKey/>")
              case _ => Right(xml)
            }
          case Left(ex) => Left(ex)
        }
      case "$colon$colon" =>
        val params: List[Either[Throwable, String]] =
          mirrorValue.asInstanceOf[List[Any]].map { param =>
            val paramType: String = getObjName(param)
            paramType match {
              case "String" | "Integer" | "Double" | "Boolean" | "Some" | "None$" | "Right" | "Left" | "Null" => coreSerialize(mirrorKey, param)
              case _ => serialize(param)
            }
          }
        flattenEitherValuesAndRightString(params) match {
          case Right(rawXml) =>
            val cleanedXml: String = rawXml.replace(s"<$mirrorKey/>", "")
            Right(s"<$mirrorKey>$cleanedXml</$mirrorKey>")
          case Left(ex) => Left(ex)
        }
      case _ => serialize(mirrorValue)
    }
  }

  def flattenEitherValuesAndRightString(eitherValues: List[Either[Throwable, String]]): Either[Throwable, String] = {
    eitherValues.collectFirst { case Left(f) => f }.toLeft {
      eitherValues.collect { case Right(r) => r }.mkString
    }
  }

}
