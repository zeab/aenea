
//Imports
import Settings._
import Dependencies._
import Docker._
import ModuleNames._
import Resolvers.allResolvers

//Add all the command alias's
CommandAlias.allCommandAlias

lazy val aenea = (project in file("."))
  .settings(rootSettings: _*)
  .settings(libraryDependencies ++= rootDependencies)
  //.enablePlugins(Artifactory)
  .settings(allResolvers: _*)

