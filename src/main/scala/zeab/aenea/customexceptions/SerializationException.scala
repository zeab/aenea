package zeab.aenea.customexceptions

final case class SerializationException(
                                         key: String,
                                         value: Any,
                                         cause: Throwable = None.orNull
                                       ) extends Exception(s"key: $key value: $value", cause)