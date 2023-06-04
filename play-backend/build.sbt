ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

classpathTypes ++= Set("ffjoystick", "dll")

lazy val root = (project in file("."))
  .settings(
    name := "force-feedback-generator-2",
      libraryDependencies ++= Seq(
          "org.apache.spark" %% "spark-core" % "3.3.2",
          "org.apache.spark" %% "spark-mllib" % "3.3.2",
          "org.platanios" %% "tensorflow" % "0.6.5",
          "org.platanios" %% "tensorflow-data" % "0.6.5",
      )
  )
