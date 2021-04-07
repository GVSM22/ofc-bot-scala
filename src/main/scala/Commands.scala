import ackcord.commands.UserCommandMessage
import ackcord.requests.Request
import ackcord.syntax.TextChannelSyntax
import akka.NotUsed

object Commands {

  def oi(message: UserCommandMessage[NotUsed]): Request[Any] = {
    message.textChannel.sendMessage(s"Oi, ${message.user.username}")
  }

  def ping(message: UserCommandMessage[NotUsed]): Request[Any] = {
    message.textChannel.sendMessage("pong!")
  }
}
