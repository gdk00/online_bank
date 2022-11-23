
ThisBuild / scalaVersion     := "2.13.8"
ThisBuild / version          := "0.1.0-SNAPSHOT"
ThisBuild / organization     := "misis"
ThisBuild / organizationName := "misis"

lazy val root = (project in file("."))
  .settings(
    name := "base-monad"
  )

