
val lwjglVersion = "3.3.2"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """play-scala-hello-world-tutorial""",
    organization := "com.example",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.13.10",
    libraryDependencies ++= Seq(
      guice,
        "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test,
        "net.java.jinput" % "jinput" % "2.0.9",
        "net.java.jinput" % "jinput" % "2.0.9" classifier "natives-all",
    ),
    scalacOptions ++= Seq(
      "-feature",
      "-deprecation",
      "-Xfatal-warnings"
    )
  )
