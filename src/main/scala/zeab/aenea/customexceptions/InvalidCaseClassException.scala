package zeab.aenea.customexceptions

final case class InvalidCaseClassException(
                                            objType: String,
                                            cause: Throwable = None.orNull
                                          ) extends Exception(s"Must be a case class at root level cannot serialize : $objType", cause)