package commands

import commands.CommandList.SimpleCommandFunction
import play.api.libs.json.Json
import response.DiscordResponse

import scala.concurrent.{ExecutionContext, Future}

class SimpleCommands(implicit ec: ExecutionContext) extends CommandList {

  private val oi: SimpleCommandFunction = message => {
    val msg = message.message.content.split(" ").tail.mkString(" ")
    Future.successful(s"O ${message.message.authorUsername} disse: $msg")
  }

  private val ping: SimpleCommandFunction = _ => {
    Future.successful("pong!")
  }

  private val help: SimpleCommandFunction = _ => {
    Future.successful(
      Json.parse(getClass.getClassLoader.getResourceAsStream("commands-help.json"))
        .\("commands")
        .asOpt[List[DiscordResponse]] match {
        case Some(value) => value.map(_.toString).mkString
        case None => "hmmm aconteceu algum erro..."
      }
    )
  }

  override def commandsWithPrefix: Seq[(String, SimpleCommandFunction)] = {
    Seq(
      ("oi", oi),
      ("ping", ping),
      ("help", help)
    )
  }
}
