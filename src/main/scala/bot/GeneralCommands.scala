package bot

import ackcord.commands.CommandMessage
import ackcord.requests.CreateMessage
import ackcord.syntax.TextChannelSyntax

object GeneralCommands {

  private def oi(message: CommandMessage[List[String]]): CreateMessage = {
    val msg = message.message.content.split(" ").tail.mkString(" ")
    message.textChannel.sendMessage(s"O ${message.message.authorUsername} disse: $msg")
  }

  private def ping(message: CommandMessage[List[String]]): CreateMessage = {
    message.textChannel.sendMessage("pong!")
  }

  def commandsWithPrefix(): Seq[(Seq[String], CommandMessage[List[String]] => CreateMessage)] = {
    Seq(
      (Seq("oi"), oi),
      (Seq("ping"), ping)
    )
  }
}
