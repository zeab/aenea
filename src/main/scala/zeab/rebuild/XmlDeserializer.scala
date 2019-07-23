package zeab.rebuild

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}
import scala.xml.{Elem, Node, NodeSeq}
import scala.xml.XML.loadString

object XmlDeserializer {

  implicit class XmlDeserialize(val input: String) extends AnyVal {
    def fromXml[Output](implicit typeTag: TypeTag[Output]): Either[Throwable, Output] ={
      implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
      Try(loadString(input)) match {
        case Success(xml) =>
          val inputType:String = typeTag.tpe.toString
          deserialize(xml.seq, inputType) match {
            case Right(obj) => Right(obj.asInstanceOf[Output])
            case Left(ex) => Left(ex)
          }
        case Failure(ex) => Left(ex)
      }
    }
  }

//  private def xmlDeserialize(xml: Seq[Node])(implicit mirror: Mirror): Either[Throwable, Any] = {
//    val inputType:String = typeTag.tpe.toString
//    //val outputClass: ClassSymbol = mirror.staticClass(inputType)
//    //deserialize()
//    ???
//  }

  private def deserialize(xml: Seq[Node], inputType:String)(implicit mirror: Mirror): Either[Throwable, Any] = {
    val outputClass: ClassSymbol = mirror.staticClass(inputType)
    val reflectedValues =
      outputClass.typeSignature.members
        .toStream.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => termSymbol }
        .map { symbol =>
          val ww = xml.toString
          val possibleNode: NodeSeq = xml \ symbol.name.toString.trim
          if (possibleNode.isEmpty) Left(new Exception(s"unable to find the node: ${symbol.name.toString.trim}"))
          else{
            coreDeserialize(possibleNode, symbol.typeSignature.resultType.toString.trim)
          }
        }
        .reverse.toList
    flattenEitherValues(reflectedValues) match {
      case Right(values) =>
        val classMirror: ClassMirror = mirror.reflectClass(outputClass)
        val constructor: MethodSymbol = outputClass.primaryConstructor.asMethod
        val constructorMirror: MethodMirror = classMirror.reflectConstructor(constructor)
        Try(constructorMirror.apply(values: _*)) match {
          case Success(instance) => Right(instance)
          case Failure(ex) => Left(ex)
        }
      case Left(ex) => Left(ex)
    }
  }

  private def coreDeserialize(xml: Seq[Node], inputType:String)(implicit mirror: Mirror): Either[Throwable, Any] = {
    inputType match {
      case "String" => Right(xml.text)
      case "Int" => Right(xml.text.toInt)
      case _ => Left(new Exception("asdasa"))
    }
  }

  //Flattens the list so that only the first left if found is kept but all the rights are unwrapped and stacked
  def flattenEitherValues(eitherValues: List[Either[Throwable, Any]]): Either[Throwable, List[Any]] = {
    eitherValues.collectFirst { case Left(f) => f }.toLeft {
      eitherValues.collect { case Right(r) => r }
    }
  }

}
