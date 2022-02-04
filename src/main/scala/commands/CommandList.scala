package commands

import ackcord.commands.CommandMessage

import scala.concurrent.{ExecutionContext, Future}

abstract class CommandList(implicit ec: ExecutionContext) {

  import commands.CommandList.SimpleCommandFunction

  def commandsWithPrefix: Seq[(String, SimpleCommandFunction)]

}

object CommandList {
  type SimpleCommandCall = CommandMessage[List[String]]
  type SimpleCommandFunction = SimpleCommandCall => Future[String]
}
