package zeab.aenea

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}
import scala.xml.XML.loadString

/**
  * An automatic case class Xml Serializer
  *
  * @author Kevin Kosnik-Downs (Zeab)
  * @since 2.12
  */
//TODO Come back to this ... https://www.tutorialspoint.com/scala/scala_data_types.htm
//TODO https://www.cs.helsinki.fi/u/wikla/OTS/Sisalto/examples/html/ch26.html
//TODO Decide what to do about empty value inside a list... do I return a blank... or do I shorten the returned list by 1
object XmlSerialize extends AeneaToolbox {

  def xmlSerialize[T](input: Any)(implicit typeTag: TypeTag[T]): Either[Throwable, T] = {
    implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
    //TODO Figure out if this is dangerous
    val inputName: String = input.getClass.getSimpleName.seq(0).toLower + input.getClass.getSimpleName.drop(1)
    serialize(input) match {
      case Right(xmlValue) =>
        val fullXml: String = s"<$inputName>$xmlValue</$inputName>"
        typeTag.tpe.typeSymbol.name.toString match {
          case "Nothing" | "String" => Right(fullXml.asInstanceOf[T])
          case "Elem" =>
            Try(loadString(fullXml)) match {
              case Success(xml) => Right(xml.asInstanceOf[T])
              case Failure(_) => Left(new Exception("Invalid Xml unable to parse"))
            }
          case _ => Left(new Exception(s"Unsupported return type: ${typeTag.tpe.typeSymbol.name.toString}"))
        }
      case Left(ex) => Left(ex)
    }
  }

  private def serialize(input: Any)(implicit mirror: Mirror): Either[Throwable, String] = {
    val inputType: Type = mirror.classSymbol(input.getClass).toType
    val inputInstance: InstanceMirror = mirror.reflect(input)
    val inputParams: Iterable[Symbol] = getObjParams(inputType)
    val possibleXml: List[Either[Throwable, String]] =
      inputParams
        .map { param =>
          //This checks for Unit types and gives back an exception if Unit is found
          Try(inputInstance.reflectField(param.asTerm).get) match {
            case Success(fieldValue) => coreSerialize(fieldValue, param.name.toString)
            case Failure(ex) => Left(ex)
          }
        }.toList
    flattenEitherValuesAndRightString(possibleXml)
  }

  private def coreSerialize(obj: Any, paramName: String)(implicit mirror: Mirror): Either[Throwable, String] = {
    //This checks for Null type and handles it accordingly rather than erring out
    val objName: String = Try(obj.getClass) match {
      case Success(clazz) => clazz.getSimpleName
      case Failure(_) => "Null"
    }
    objName match {
      case "Some" | "None$" =>
        obj.asInstanceOf[Option[Any]] match {
          case Some(actualValue) => coreSerialize(actualValue, paramName)
          case None => Right(s"<$paramName/>")
        }
      case "$colon$colon" =>
        val theList: List[Either[Throwable, String]] =
          obj.asInstanceOf[List[Any]]
            .map { node => coreSerialize(node, paramName) }
        flattenEitherValuesAndRightString(theList)
      case "Right" | "Left" => Left(new Exception(s"Unsupported Type for Serialization: Either"))
      case "Null" => Right(s"<$paramName/>")
      case _ =>
        if (isPrimitive(objName)) Right(s"<$paramName>$obj</$paramName>")
        else
          serialize(obj) match {
            case Right(xml) => Right(s"<$paramName>$xml</$paramName>")
            case Left(ex) => Left(ex)
          }
    }
  }

}
