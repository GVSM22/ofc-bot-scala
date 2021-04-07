name := "ofc-bot-scala"

version := "0.1"

scalaVersion := "2.13.5"

scalacOptions ++= Seq(
  "-Xfatal-warnings"
)

resolvers += Resolver.JCenterRepository
resolvers += "Typesafe repository" at "https://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "net.katsstuff" %% "ackcord" % "0.17.1"
libraryDependencies += "net.katsstuff" %% "ackcord-commands"        % "0.17.1"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.8.1"
