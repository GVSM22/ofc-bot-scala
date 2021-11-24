package commands

import ackcord.commands._
import ackcord.requests.{CreateMessage, Requests}
import ackcord.syntax.TextChannelSyntax
import akka.stream.scaladsl.Flow

class CommandsBuilder(requests: Requests, prefix: String, commands: Seq[CommandList])
  extends CommandController(requests) {

  private val newCommand = CommandBuilder(
    requests = requests,
    defaultMustMention = false,
    defaultMentionOrPrefix = true,
    parser = MessageParser.allStringsParser,
    actionFunction = ActionFunction.identity
  ).named(Seq(prefix), _: Seq[String])

  val allCommands: Seq[NamedCommand[List[String]]] = for {
      command <- commands
      (commandName, command) <- command.commandsWithPrefix
    } yield newCommand(Seq(commandName)).toSink(
      Flow[CommandMessage[List[String]]]
        .mapAsyncUnordered[CreateMessage](requests.parallelism)(userMsg =>
          command(userMsg).map(commandResult => userMsg.textChannel.sendMessage(commandResult)))
        .to(requests.sinkIgnore)
    )
}
