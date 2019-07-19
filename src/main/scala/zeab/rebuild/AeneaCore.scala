package zeab.rebuild

//Imports
import scala.reflect.runtime.universe._
import scala.util.{Failure, Success, Try}
trait AeneaCore {

  def getObjName(obj:Any): String =
    Try(obj.getClass.getSimpleName) match {
      case Success(name) => name
      case Failure(_) => "Null"
    }

}
