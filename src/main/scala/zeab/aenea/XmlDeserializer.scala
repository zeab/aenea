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
import scala.xml.{Elem, Node, NodeSeq}
import scala.xml.XML.loadString

object XmlDeserializer {

  implicit class XmlDeserialize11(val input: String) extends AnyVal {
    def fromXml[T](implicit typeTag: TypeTag[T]): Either[Throwable, T] ={
      implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)
      Try(loadString(input)) match {
        case Success(xml) =>
          val inputType:String = typeTag.tpe.toString
          ddd(inputType, xml) match {
            case Right(obj) => Right(obj.asInstanceOf[T])
            case Left(ex) => Left(ex)
          }
        case Failure(ex) => Left(ex)
      }
    }
  }

  def ddd(inputType: String, xml: Seq[Node])(implicit mirror: Mirror):  Either[Throwable, Any] ={
    val reflectedClass: ClassSymbol = mirror.staticClass(inputType)
    val reflectedValues =
      reflectedClass.typeSignature.members
        .toStream.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => termSymbol }
        .map { symbol => (symbol.name.toString.trim, symbol.typeSignature.resultType.toString.trim) }
        .reverse.toList

    println()
    ???
  }

  def deser(xml: Elem, inputType:String)(implicit mirror: Mirror): Either[Throwable, Any] ={
    val reflectedClass: ClassSymbol = mirror.staticClass(inputType)
    val reflectedValues =
      reflectedClass.typeSignature.members
        .toStream.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => termSymbol }
        .map { symbol => (symbol.name.toString.trim, symbol.typeSignature.resultType.toString.trim) }
        .reverse.toList
    val ggg = reflectedValues
      .map{ symbolInfo =>
        val (symbolName, symbolType): (String, String) = symbolInfo
        //coreDeserialize(xml, symbolName, symbolType )
        symbolInfo
      }
    println()
    ???
  }

  private def deserialize[T](xml: Elem)(implicit mirror: Mirror, typeTag: TypeTag[T]): Either[Throwable, T] = {
    val inputType:String = typeTag.tpe.toString
    val reflectedClass: ClassSymbol = mirror.staticClass(inputType)
    val reflectedValues =
      reflectedClass.typeSignature.members
        .toStream.collect { case termSymbol: TermSymbol if !termSymbol.isMethod => termSymbol }
        .map { symbol => (symbol.name.toString.trim, symbol.typeSignature.resultType.toString.trim) }
        .reverse.toList
    val ggg = reflectedValues
        .map{ symbolInfo =>
          val (symbolName, symbolType): (String, String) = symbolInfo
          coreDeserialize(xml, symbolName, symbolType )
        }
    println()
    flattenEitherValues(ggg) match {
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

  private def coreDeserialize[T](xml: Elem, symName:String, synType:String)(implicit mirror: Mirror,  typeTag: TypeTag[T]): Either[Throwable, Any] = {
    val possibleNode: NodeSeq = xml \ symName
    val ee = possibleNode.size
    synType match {
      case "String" => Right(possibleNode.text)
      case "Int" => Right(possibleNode.text.toInt)
      case "Float" => Left(new Exception("not imp"))
      case "Long" => Left(new Exception("not imp"))
      case "Short" => Left(new Exception("not imp"))
      case "Double" => Left(new Exception("not imp"))
      case "Boolean" => Left(new Exception("not imp"))
      case "List" => Left(new Exception("not imp"))
      case "Vector" => Left(new Exception("not imp"))
      case _ =>

        val g = Elem.apply(xml.prefix, xml.label, xml.attributes, xml.scope, true, xml.child :_*)
        deserialize[T](g)
    }
  }

  //Flattens the list so that only the first left if found is kept but all the rights are unwrapped and stacked
  def flattenEitherValues(eitherValues: List[Either[Throwable, Any]]): Either[Throwable, List[Any]] = {
    eitherValues.collectFirst { case Left(f) => f }.toLeft {
      eitherValues.collect { case Right(r) => r }
    }
  }

}
