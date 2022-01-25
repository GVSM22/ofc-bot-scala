package commands

import ackcord.commands.CommandMessage
import play.api.libs.json.Json
import response.DiscordResponse

import scala.concurrent.{ExecutionContext, Future}

class SimpleCommands(implicit ec: ExecutionContext) extends CommandList {

  private def oi(message: CommandMessage[List[String]]): Future[String] = {
    val msg = message.message.content.split(" ").tail.mkString(" ")
    Future.successful(s"O ${message.message.authorUsername} disse: $msg")
  }

  private def ping(message: CommandMessage[List[String]]): Future[String] = {
    Future.successful("pong!")
  }

  private def help(message: CommandMessage[List[String]]): Future[String] = {
    Future.successful(
      Json.parse(getClass.getClassLoader.getResourceAsStream("commands-help.json"))
        .\("commands")
        .asOpt[List[DiscordResponse]] match {
        case Some(value) => value.map(_.toString).mkString
        case None => "hmmm aconteceu algum erro..."
      }
    )
  }

  override def commandsWithPrefix: Seq[(String, CommandMessage[List[String]] => Future[String])] = {
    Seq(
      ("oi", oi),
      ("ping", ping),
      ("help", help)
    )
  }
}
