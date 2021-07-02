package bot

import ackcord.{APIMessage, ClientSettings}
import com.typesafe.config.{Config, ConfigFactory}

import scala.util.{Failure, Success}

object BotEntry {
  private val config: Config = ConfigFactory.load().getConfig("bot")
  private val token: String = config.getString("token")
  private val prefix: String = config.getString("prefix")

  private val clientSettings: ClientSettings = ClientSettings(token)
  import clientSettings.executionContext

  def run(): Unit = {
    clientSettings.createClient().onComplete {
      case Success(client) => client.onEventSideEffectsIgnore {
        case APIMessage.Ready(_) => println("ready!")
      }
        val myCommands = new Commands(client.requests, prefix)
        client.commands.bulkRunNamed(myCommands.allCommands: _*)
        client.login()
      case Failure(exception) => println(exception)
    }
  }
}
