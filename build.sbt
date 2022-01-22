name := "ofc-bot-scala"

version := "0.1"

scalaVersion := "2.13.5"

scalacOptions ++= Seq(
  "-Xfatal-warnings"
)

resolvers += Resolver.JCenterRepository
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "net.katsstuff" %% "ackcord" % "0.17.1"
libraryDependencies += "net.katsstuff" %% "ackcord-commands" % "0.17.1"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.1"
libraryDependencies ++= Seq(
  "com.typesafe.slick" %% "slick" % "3.3.3",
  "org.slf4j" % "slf4j-nop" % "1.6.4",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3"
)
libraryDependencies += "org.postgresql" % "postgresql" % "42.2.23"
libraryDependencies += "com.typesafe" % "config" % "1.4.1"
// https://mvnrepository.com/artifact/org.liquibase/liquibase-core
libraryDependencies += "org.liquibase" % "liquibase-core" % "4.6.2"
// https://mvnrepository.com/artifact/org.yaml/snakeyaml
libraryDependencies += "org.yaml" % "snakeyaml" % "1.29"
