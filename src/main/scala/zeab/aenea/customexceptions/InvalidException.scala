package zeab.aenea.customexceptions

final case class InvalidException(
                                   private val message: String = "user id not found",
                                   private val cause: Throwable = None.orNull
                                 ) extends Exception(message, cause)