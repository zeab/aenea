package zeab.aenea

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}
import scala.xml.XML.loadString
import scala.xml.{Elem, NodeSeq}

/**
  * An automatic case class Xml Deserializer
  *
  * @author Kevin Kosnik-Downs (Zeab)
  * @since 2.12
  */
object XmlDeserialize extends AeneaToolbox {

  //Entry way into deserialize... this is where all the magic starts
  def xmlDeserialize[T](input: String)(implicit typeTag: TypeTag[T]): Either[Throwable, T] = {
    implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
    Try(loadString(input)) match {
      case Success(xml) =>
        deserialize(List(typeTag.tpe.toString), xml) match {
          case Right(obj) => Right(obj.asInstanceOf[T])
          case Left(ex) => Left(ex)
        }
      case Failure(ex) => Left(ex)
    }
  }

  //Main grunt worker to process each case class
  private def deserialize(typeNames: List[String], xml: Elem)(implicit mirror: Mirror): Either[Throwable, Any] = {
    typeNames.headOption match {
      case Some(typeName) =>
        val reflectedClass: ClassSymbol = mirror.staticClass(typeName)
        val reflectedType: Type = reflectedClass.typeSignature
        val reflectedValue: List[Either[Throwable, Any]] =
          getObjParams(reflectedType)
            .map { param =>
              val (paramName, paramTypes): (String, List[String]) = getSymbolInfo(param)
              coreDeserialize(paramName, paramTypes, xml)
            }.toList
        flattenEitherValues(reflectedValue) match {
          case Right(values) =>
            val classMirror: ClassMirror = mirror.reflectClass(reflectedClass)
            val constructor: MethodSymbol = reflectedClass.primaryConstructor.asMethod
            val constructorMirror: MethodMirror = classMirror.reflectConstructor(constructor)
            Try(constructorMirror.apply(values: _*)) match {
              case Success(instance) => Right(instance)
              case Failure(ex) => Left(ex)
            }
          case Left(ex) => Left(ex)
        }
      case None => Left(new Exception("Empty Type Names list... something broken..."))
    }
  }

  //The bit that actually takes a value and attempts to return it as the value of the type asked
  private def coreDeserialize(paramName: String, paramTypes: List[String], xml: Elem)(implicit mirror: Mirror): Either[Throwable, Any] = {
    val possibleNodeSeq: NodeSeq = xml \ paramName
    if (possibleNodeSeq.isEmpty) Left(new Exception(s"Could not found node: $paramName"))
    else {
      val possibleNodeValue: String = possibleNodeSeq.text
      paramTypes.headOption.getOrElse("") match {
        case "String" | "Any" => Right(possibleNodeValue)
        //      case "Char" =>
        //        //TODO Check up on char
        //        //This may or may not be right... but...idk if its every used by anyone so low pri
        //        Try((xml \ paramName).text.headOption.getOrElse(' ')) match {
        //          case Success(value) =>
        //            value match {
        //              case ' ' => value
        //              case _ => 'E'
        //            }
        //          case Failure(_) => ' '
        //        }
        case "Byte" => returnMatch(Try(possibleNodeValue.toByte))
        case "Float" => returnMatch(Try(possibleNodeValue.toFloat))
        case "Long" => returnMatch(Try(possibleNodeValue.toLong))
        case "Short" => returnMatch(Try(possibleNodeValue.toShort))
        case "Double" => returnMatch(Try(possibleNodeValue.toDouble))
        case "Int" => returnMatch(Try(possibleNodeValue.toInt))
        case "Boolean" => returnMatch(Try(possibleNodeValue.toBoolean))
        case "List" =>
          if (possibleNodeSeq.toString == s"<$paramName/>") Right(List.empty)
          else {
            val theList: List[Either[Throwable, Any]] =
              possibleNodeSeq.map { node =>
                //Have to add a root tag so that it can be "found" inside the xml... the text is irrelevant existence is all that matters
                coreDeserialize(paramName, paramTypes.drop(1), <root>{node.asInstanceOf[Elem]}</root>)
              }.toList
            //Flatten so that we only keep
            flattenEitherValues(theList)
          }
        case "Option" =>
          //When is it allowed to not find anything
          val optionValue: Either[Throwable, Any] = coreDeserialize(paramName, paramTypes.drop(1), xml)
          if (optionValue.isLeft) Right(None)
          else
            optionValue match {
              case Right(value) =>
                value match {
                  case "" => Right(None)
                  case _ => Right(Some(value))
                }
              case Left(ex) => Left(ex)
            }
        case "Unit" | "Either" => Left(new Exception("Error!! Unsupported Type"))
        case _ => deserialize(paramTypes, loadString(possibleNodeSeq.toString))
      }
    }
  }

  private def getSymbolInfo(symbol: Symbol): (String, List[String]) = {
    val paramName: String = symbol.name.toString
    val paramTypes: List[String] = symbol.typeSignature.resultType.toString
      .split("\\[")
      .map(_.replace("]", ""))
      .toList
    (paramName, paramTypes)
  }

  private def returnMatch(theTry:Try[Any]): Either[Throwable, Any] =
    theTry match {
      case Success(value) => Right(value)
      case Failure(ex) => Left(ex)
    }

}
