import ackcord.commands.{CommandController, NamedCommand, NamedCommandBuilder, UserCommandMessage}
import ackcord.requests.Requests
import akka.NotUsed

class MyCommands(requests: Requests, prefix: String) extends CommandController(requests){

  val newCommand: Seq[String] => NamedCommandBuilder[UserCommandMessage, NotUsed] = Command.named(Seq(prefix), _)

  val commands: Seq[NamedCommand[NotUsed]] = Seq(
    (Seq("oi"), Commands.oi _),
    (Seq("ping"), Commands.ping _)
  ).map{case (command, function) => newCommand(command).withRequest(function)}
}
