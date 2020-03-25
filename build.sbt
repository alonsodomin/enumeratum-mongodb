scalaVersion := "2.12.11"

libraryDependencies ++= Seq(
  "org.mongodb.scala"      %% "mongo-scala-driver"   % "2.9.0",
  "com.beachape"           %% "enumeratum"           % "1.5.15",

  "org.slf4j"              % "slf4j-api"            % "1.7.30" % Test,
  "org.slf4j"              % "slf4j-simple"         % "1.7.30" % Test,

  "org.scalatest"          %% "scalatest"            % "3.1.1" % Test,
  "com.github.simplyscala" %% "scalatest-embedmongo" % "0.2.4" % Test
)