package commands

import ackcord.commands._
import ackcord.requests.{CreateMessage, Requests}
import ackcord.syntax.TextChannelSyntax
import akka.stream.scaladsl.Flow
import commands.CommandList.{SimpleCommandCall, SimpleCommandFunction}

import scala.concurrent.Future

class CommandsBuilder(requests: Requests, prefix: String, commands: Seq[CommandList]) extends CommandController(requests) {

  private val newCommand = CommandBuilder[CommandMessage, List[String]](
    requests = requests,
    defaultMustMention = false,
    defaultMentionOrPrefix = true,
    parser = MessageParser.allStringsParser,
    actionFunction = ActionFunction.identity
  ).named(Seq(prefix), _)

  val allCommands: Seq[NamedCommand[List[String]]] = for {
    command <- commands
    (commandName, commandFunction) <- command.commandsWithPrefix
  } yield newCommand(Seq(commandName)).toSink(
    Flow[SimpleCommandCall]
      .mapAsyncUnordered[CreateMessage](requests.parallelism)(executeAndRespond(commandFunction, _))
      .to(requests.sinkIgnore)
  )

  private def executeAndRespond(commandFunction: SimpleCommandFunction,
                                commandCall: SimpleCommandCall): Future[CreateMessage] =
    commandFunction(commandCall).map(commandCall.textChannel.sendMessage(_))
}
