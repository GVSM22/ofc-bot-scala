import BotEntry.clientSettings.executionContext
import ackcord.{APIMessage, ClientSettings}
import play.api.libs.json.{JsValue, Json}
import utils.implicits.StringUtils.Formatter

import java.io.InputStream
import scala.util.{Failure, Success}

object BotEntry {

  val fileStream: InputStream = this.getClass.getClassLoader.getResourceAsStream("config.json")
  val configJson: JsValue = Json.parse(fileStream)
  val token: String = Json.stringify(configJson("token")).removeQuotes()
  val prefix: String = Json.stringify(configJson("prefix")).removeQuotes()

  val clientSettings: ClientSettings = ClientSettings(token)

  clientSettings.createClient().onComplete {
    case Success(client) =>
      client.onEventSideEffectsIgnore {
        case APIMessage.Ready(_) => println("ready!")
      }
      val myCommands = new MyCommands(client.requests, prefix)
      client.commands.bulkRunNamed(myCommands.commands: _*)
      client.login()
    case Failure(exception) => println(exception)
  }
}
