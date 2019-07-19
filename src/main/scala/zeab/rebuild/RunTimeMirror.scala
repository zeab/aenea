package zeab.rebuild

import scala.reflect.runtime.universe._

object RunTimeMirror {

  implicit val mirror: Mirror = runtimeMirror(getClass.getClassLoader)

}
