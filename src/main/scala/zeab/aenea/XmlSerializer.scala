package zeab.aenea

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}

object XmlSerializer {

  implicit class XmlSerialize(val obj: Any) extends AnyVal {
    def asXml: Either[Throwable, String] = {
      implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
      serialize(obj)
    }
  }

  private def serialize(obj: Any)(implicit mirror: Mirror): Either[Throwable, String] = {
    val objName: String = getObjName(obj)
    objName match {
      case "String" | "Integer" | "Double" | "Boolean" | "Short" | "Long" | "Float" | "Some" | "None$" | "Right" | "Left" | "Null" | "Unit" | "Nil$" =>
        Left(new Exception("cannot serialize on a primitive"))
      case "Vector" | "$colon$colon" => Left(new Exception("not implemented"))
      case x if x.contains("Map") => Left(new Exception("not implemented"))
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

  private def coreSerialize(mirrorKey: String, mirrorValue: Any)(implicit mirror: Mirror): Either[Throwable, String] =
    getObjName(mirrorValue) match {
      case "String" | "Integer" | "Double" | "Boolean" | "Long" | "Short" | "Float" =>
        mirrorValue.toString match {
          case "" => Right(s"<$mirrorKey/>")
          case _ => Right(s"<$mirrorKey>$mirrorValue</$mirrorKey>")
        }
      case "Some" | "None$" =>
        mirrorValue.asInstanceOf[Option[Any]] match {
          case Some(optionValue) => coreSerialize(mirrorKey, optionValue)
          case None => Right(s"<$mirrorKey/>")
        }
      case "Nil$" => Right(s"<$mirrorKey/>")
      case "Right" | "Left" => Left(new Exception("unsupported Left|Right"))
      case "Vector" =>
        val params: List[Either[Throwable, String]] =
          mirrorValue.asInstanceOf[Vector[Any]].map { param => serializeDecider(param, mirrorKey) }.toList
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
          mirrorValue.asInstanceOf[List[Any]].map { param => serializeDecider(param, mirrorKey) }
        flattenEitherValuesAndRightString(params) match {
          case Right(rawXml) =>
            val cleanedXml: String = rawXml.replace(s"<$mirrorKey/>", "")
            Right(s"<$mirrorKey>$cleanedXml</$mirrorKey>")
          case Left(ex) => Left(ex)
        }
      case _ =>
        serialize(mirrorValue) match {
          case Right(xml) => Right(s"<$mirrorKey>$xml</$mirrorKey>")
          case Left(ex) => Left(ex)
        }
    }

  private def flattenEitherValuesAndRightString(eitherValues: List[Either[Throwable, String]]): Either[Throwable, String] =
    eitherValues.collectFirst { case Left(f) => f }.toLeft {
      eitherValues.collect { case Right(r) => r }.mkString
    }

  private def getObjName(obj: Any): String =
    Try(obj.getClass.getSimpleName) match {
      case Success(name) => name
      case Failure(_) => "Null"
    }

  private def serializeDecider(param: Any, mirrorKey: String)(implicit mirror: Mirror): Either[Throwable, String] =
    getObjName(param) match {
      case "String" | "Integer" | "Double" | "Boolean" | "Long" | "Short" | "Float" | "Some" | "None$" | "Right" | "Left" | "Null" => coreSerialize(mirrorKey, param)
      case _ => serialize(param)
    }

}
