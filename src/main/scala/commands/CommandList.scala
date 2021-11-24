package commands

import ackcord.commands.CommandMessage

import scala.concurrent.{ExecutionContext, Future}

abstract class CommandList(implicit ec: ExecutionContext) {

  def commandsWithPrefix: Seq[(String, CommandMessage[List[String]] => Future[String])]

}
