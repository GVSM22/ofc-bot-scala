package commands

import commands.CommandList.SimpleCommandFunction
import database.DatabaseLayer
import slick.jdbc.PostgresProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class MessageCommands(implicit ec: ExecutionContext) extends CommandList with DatabaseLayer {

  private val insertMessage: SimpleCommandFunction = message => {
    val msg = message.message.content.split(" ").tail
    val messageMap = List("message", "category").zip(msg).toMap
    db.run(DBIO.seq(messages += (1, messageMap("message"), messageMap("category"))))
      .map(_ => "message inserted!")
  }

  private val getSomeMessages: SimpleCommandFunction = receivedMessage => {
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
          "Algo deu errado enquanto buscava suas mensagens (╥﹏╥)"
      }
  }

  override def commandsWithPrefix: Seq[(String, SimpleCommandFunction)] = Seq(
    ("insert", insertMessage),
    ("getMessages", getSomeMessages)
  )
}
