package zeab.aenea

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}

object XmlSeri {

  def xmlSerialize[T](obj: Any)(implicit typeTag: TypeTag[T]): Either[Throwable, String] = {
    implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
    val objName: String =
      Try(obj.getClass.getSimpleName) match {
        case Success(name) => name
        case Failure(_) => "Null"
      }
    //reject stuff flat out if its just a value or unsupported type
    if (objName == "String" | objName == "Integer" | objName == "Double" | objName == "Null" | objName == "Some" | objName == "None$" | objName == "Right" | objName == "Left")
      Left(new Exception("unable to seri a primitive"))
    else
      serialize(obj)
  }

  def serialize(obj: Any)(implicit mirror: Mirror): Either[Throwable, String] = {
    val objName: String =
      Try(obj.getClass.getSimpleName) match {
        case Success(name) => name
        case Failure(_) => "Null"
      }
    val objInstanceMirror: InstanceMirror = mirror.reflect(obj)
    val possibleXml =
      objInstanceMirror.symbol.typeSignature.members.toStream.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => objInstanceMirror.reflectField(termSymbol) }
        .map { fieldMirror => fieldMirror.symbol.name.toString -> fieldMirror.get }
        .reverse.toMap
        .map { param =>
          val (param1, param2) = param
          val paramType =
            Try(param2.getClass.getSimpleName) match {
              case Success(name) => name
              case Failure(_) => "Null"
            }
          paramType match {
            case "String" | "Integer" | "Boolean" | "Double" => Right(s"<$param1>$param2</$param1>")
            case _ => serialize(param2)
          }
        }.toList
    val eee =
      flattenEitherValuesAndRightString(possibleXml) match {
        case Right(value) => Right(value.mkString(s"<$objName>", "", s"</$objName>"))
        case Left(ex) => Left(ex)
      }
    println()
    eee
  }

  def flattenEitherValuesAndRightString(eitherValues: List[Either[Throwable, String]]): Either[Throwable, String] = {
    eitherValues.collectFirst { case Left(f) => f }.toLeft {
      eitherValues.collect { case Right(r) => r }.mkString
    }
  }

  private def coreSerialize(obj: Any)(implicit mirror: Mirror): Either[Throwable, String] = {
    val objName: String =
      Try(obj.getClass.getSimpleName) match {
        case Success(name) => name
        case Failure(_) => "Null"
      }
    println()
    ???
  }

}
