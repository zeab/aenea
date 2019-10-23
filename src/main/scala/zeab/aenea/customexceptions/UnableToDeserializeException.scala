package zeab.aenea.customexceptions

final case class UnableToDeserializeException(
                                               private val message: String,
                                               private val cause: Throwable = None.orNull
                                             ) extends Exception(message, cause)