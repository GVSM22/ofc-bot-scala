package bot

import ackcord.commands.{CommandBuilder, CommandController, NamedCommand, NamedCommandBuilder}
import ackcord.requests.Requests
import ackcord.commands._

class Commands(requests: Requests, prefix: String) extends CommandController(requests){

  private val newCommand = CommandBuilder(
    requests = requests,
    defaultMustMention = false,
    defaultMentionOrPrefix = true,
    parser = MessageParser.allStringsParser,
    actionFunction = ActionFunction.identity
  ).named(Seq(prefix), _: Seq[String])

  private val commandFunctions = GeneralCommands.commandsWithPrefix()

  val allCommands: Seq[NamedCommand[List[String]]] = commandFunctions.map{
    case (command, function) => newCommand(command).withRequest(function)
  }
}
