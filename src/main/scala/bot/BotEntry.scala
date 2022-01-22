package bot

import ackcord.APIMessage
import commands.{CommandsBuilder, CommandsLayer}
import database.DatabaseLayer

import scala.util.{Failure, Success, Try}

class BotEntry extends ConfigLayer
  with DatabaseLayer
  with CommandsLayer {

  def run(): Unit = {
    clientSettings.createClient().onComplete {
      case Success(client) => client.onEventSideEffectsIgnore {
        case APIMessage.Ready(_) =>
          Try {
            val connection = db.createSession().conn
            runMigrations(connection)
          } match {
            case Failure(exception) => println(s"Deu ruim com o banco: $exception")
            case Success(_) => println(s"ready!")
          }
      }
        val myCommands = new CommandsBuilder(client.requests, prefix, commands)
        client.commands.bulkRunNamed(myCommands.allCommands: _*)
        client.login()
      case Failure(exception) => println(s"Deu ruim com o Discord: $exception")
    }
  }
}
