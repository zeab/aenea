package zeab.aenea

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}

object XmlSerializer {

  implicit class XmlSerialize(obj: Any) {
    implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
    def asXml: Either[Throwable, String] =
      serialize(obj)
  }

  private def serialize(obj: Any)(implicit mirror: Mirror): Either[Throwable, String] = {
    val objName: String = getObjName(obj)
    objName match {
      case "String" | "Integer" | "Double" | "Boolean" | "Short" | "Long" | "Float" | "Some" | "None$" | "Right" | "Left" | "Null" | "Unit" =>
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
        Left(new Exception("not implemented"))
      case "$colon$colon" =>
        val params: List[Either[Throwable, String]] =
          obj.asInstanceOf[List[Any]].map { param => serialize(param) }
        flattenEitherValuesAndRightString(params)
        Left(new Exception("not implemented"))
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

  private def coreSerialize(mirrorKey: String, mirrorValue: Any)(implicit mirror: Mirror): Either[Throwable, String] = {
    val mirrorValueType: String = getObjName(mirrorValue)
    mirrorValueType match {
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
      case "Right" | "Left" =>
        Left(new Exception("unsupported Left|Right"))
      case "Vector" =>
        val params: List[Either[Throwable, String]] =
          mirrorValue.asInstanceOf[Vector[Any]].map { param =>
            serializeDecider(param, mirrorKey)
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
            serializeDecider(param, mirrorKey)
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

  private def flattenEitherValuesAndRightString(eitherValues: List[Either[Throwable, String]]): Either[Throwable, String] = {
    eitherValues.collectFirst { case Left(f) => f }.toLeft {
      eitherValues.collect { case Right(r) => r }.mkString
    }
  }

  private def getObjName(obj:Any): String =
    Try(obj.getClass.getSimpleName) match {
      case Success(name) => name
      case Failure(_) => "Null"
    }

  private def serializeDecider(param:Any, mirrorKey:String)(implicit mirror: Mirror): Either[Throwable, String] ={
    val paramType: String = getObjName(param)
    paramType match {
      case "String" | "Integer" | "Double" | "Boolean" | "Some" | "None$" | "Right" | "Left" | "Null" => coreSerialize(mirrorKey, param)
      case _ => serialize(param)
    }
  }

}
