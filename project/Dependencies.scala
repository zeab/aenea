//Imports
import sbt._

object Dependencies {

  //List of Versions
  val V = new {
    val reflect                     = "2.12.8"
    val scalaTest                   = "3.0.5"
    val scalaXML                    = "1.2.0"
  }

  //List of Dependencies
  val D = new {
    val reflect                     = "org.scala-lang" % "scala-reflect" % V.reflect
    //Test
    val scalaTest                   = "org.scalatest" %% "scalatest" % V.scalaTest
    //Xml
    val scalaXML                    = "org.scala-lang.modules" %% "scala-xml" % V.scalaXML
  }

  val rootDependencies: Seq[ModuleID] = Seq(
    D.reflect,
    D.scalaTest,
    D.scalaXML
  )

}
