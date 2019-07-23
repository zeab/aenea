//package zeab.obsolte
//
///**
//  * A collection of useful functions for supporting Aenea's other jobs
//  *
//  * @author Kevin Kosnik-Downs (Zeab)
//  * @since 2.12
//  */
//trait AeneaToolbox {
//
//  def getObjParams(inputType: Type): Iterable[Symbol] = {
//    inputType.decls
//      .filter { param =>
//        //It only matters since primitives have values that start with _ that we do not need to reflect on
//        //Checks if the value is a primitive or not to see if we need to filter out values starting with _
//        val valueCheck: Boolean =
//          if (isPrimitive(inputType.typeSymbol.name.toString)) {
//            "value [^_].*".r.findFirstIn(param.toString) match {
//              case Some(_) => true
//              case None => false
//            }
//          }
//          else {
//            "value .*".r.findFirstIn(param.toString) match {
//              case Some(_) => true
//              case None => false
//            }
//          }
//        val endOfLineSpaceCheck: Boolean = "(?<!\\s)$".r.findFirstIn(param.name.toString) match {
//          case Some(_) => true
//          case None => false
//        }
//        valueCheck & endOfLineSpaceCheck
//      }
//  }
//
//  //Flattens the list so that only the first left if found is kept but all the rights are unwrapped and stacked
//  def flattenEitherValues(eitherValues: List[Either[Throwable, Any]]): Either[Throwable, List[Any]] = {
//    eitherValues.collectFirst { case Left(f) => f }.toLeft {
//      eitherValues.collect { case Right(r) => r }
//    }
//  }
//
//  //Flattens the list and makes all the right value one long string
//  def flattenEitherValuesAndRightString(eitherValues: List[Either[Throwable, String]]): Either[Throwable, String] = {
//    eitherValues.collectFirst { case Left(f) => f }.toLeft {
//      eitherValues.collect { case Right(r) => r }.mkString
//    }
//  }
//
//  def isPrimitive(nameToCheck: String): Boolean = {
//    //TODO Now that im only using class names I can remove some of the dupe tags in here...one day...
//    val primitives: List[String] = List("Double", "Float", "Long", "Int", "Integer", "Short", "Byte", "Char", "Character", "Unit", "Boolean", "String")
//    primitives.contains(nameToCheck)
//  }
//
//}
