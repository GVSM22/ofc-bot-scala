package commands

import ackcord.commands.CommandMessage
import database.DatabaseLayer
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class MessageCommands(implicit ec: ExecutionContext) extends CommandList
  with DatabaseLayer {

  private def insertMessage(message: CommandMessage[List[String]]): Future[String] = {
    val msg = message.message.content.split(" ").tail
    val messageMap = List("message", "category").zip(msg).toMap
    db.run(DBIO.seq(messages += (1, messageMap("message"), messageMap("category"))))
      .map(_ =>  "message inserted!")
  }

  private def getSomeMessages(receivedMessage: CommandMessage[List[String]]): Future[String] = {
    type MessageResponse = (Int, String, String)
    val condenseRows: Seq[MessageResponse] => String = messageRows => {
      messageRows.map {
        case (_, message, category) => s"Category: $category,\nMessage: $message"
      }.mkString("\n\n")
    }

    def selectMessages(limit: Option[Int]): Future[Seq[MessageResponse]] = limit match {
      case Some(dataLimit) => db.run(messages.take(dataLimit).result)
      case None => db.run(messages.result)
    }

    val msg = receivedMessage.message.content.split(" ").tail.mkString("")
    selectMessages(msg.toIntOption)
      .map(condenseRows)
      .recover {
        case exception =>
          println(exception)
          "An error occurred!"
      }
  }

  override def commandsWithPrefix: Seq[(String, CommandMessage[List[String]] => Future[String])] = Seq(
    ("insert", insertMessage),
    ("getMessages", getSomeMessages)
  )
}
