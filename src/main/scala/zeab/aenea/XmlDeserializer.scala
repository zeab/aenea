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
import scala.xml.{Elem, NodeSeq}
import scala.xml.XML.loadString

object XmlDeserializer {

  implicit class XmlDeserialize11(val input: String) extends AnyVal {
    def fromXml[T](implicit typeTag: TypeTag[T]): Either[Throwable, T] ={
      implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
      Try(loadString(input)) match {
        case Success(xml) => deserialize[T](xml)
        case Failure(ex) => Left(ex)
      }
    }
  }

  private def deserialize[T](xml: Elem)(implicit mirror: Mirror, typeTag: TypeTag[T]): Either[Throwable, T] = {
    val inputType:String = typeTag.tpe.toString
    val reflectedClass: ClassSymbol = mirror.staticClass(inputType)
    val reflectedType: Type = reflectedClass.typeSignature
    val reflectedValues =
      reflectedType.members.toStream.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => termSymbol }
        .map { symbol =>
          (symbol.name.toString.trim, symbol.typeSignature.resultType.toString.trim)
        }.reverse.toList
        .map{x =>
      coreDeserialize(xml, x._1, x._2 )
    }
    flattenEitherValues(reflectedValues) match {
      case Right(values) =>
        val classMirror: ClassMirror = mirror.reflectClass(reflectedClass)
        val constructor: MethodSymbol = reflectedClass.primaryConstructor.asMethod
        val constructorMirror: MethodMirror = classMirror.reflectConstructor(constructor)
        Try(constructorMirror.apply(values: _*)) match {
          case Success(instance) => Right(instance.asInstanceOf[T])
          case Failure(ex) => Left(ex)
        }
      case Left(ex) => Left(ex)
    }
  }

  private def coreDeserialize(xml: Elem, symName:String, synType:String)(implicit mirror: Mirror): Either[Throwable, Any] = {
    val possibleNodeSeq: NodeSeq = xml \ symName
    synType match {
      case "String" => Right(possibleNodeSeq.text)
      case "Int" => Right(possibleNodeSeq.text.toInt)
      case _ => deserialize(xml)
    }
  }

  //Flattens the list so that only the first left if found is kept but all the rights are unwrapped and stacked
  def flattenEitherValues(eitherValues: List[Either[Throwable, Any]]): Either[Throwable, List[Any]] = {
    eitherValues.collectFirst { case Left(f) => f }.toLeft {
      eitherValues.collect { case Right(r) => r }
    }
  }

}
