package bot

import ackcord.ClientSettings
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.ExecutionContext

trait ConfigLayer {

  val config: Config = ConfigFactory.load().getConfig("bot")
  val token: String = config.getString("token")
  val prefix: String = config.getString("prefix")
  val clientSettings: ClientSettings = ClientSettings(token)
  implicit val ec: ExecutionContext = clientSettings.executionContext
}
