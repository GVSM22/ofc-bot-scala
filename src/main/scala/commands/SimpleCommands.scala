package commands

import ackcord.commands.CommandMessage

import scala.concurrent.{ExecutionContext, Future}

class SimpleCommands(implicit ec: ExecutionContext) extends CommandList {

  private def oi(message: CommandMessage[List[String]]): Future[String] = {
    val msg = message.message.content.split(" ").tail.mkString(" ")
    Future.successful(s"O ${message.message.authorUsername} disse: $msg")
  }

  private def ping(message: CommandMessage[List[String]]): Future[String] = {
    Future.successful("pong!")
  }

  override def commandsWithPrefix: Seq[(String, CommandMessage[List[String]] => Future[String])] = {
    Seq(
      ("oi", oi),
      ("ping", ping)
    )
  }
}
