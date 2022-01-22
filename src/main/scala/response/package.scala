package response

import play.api.libs.functional.syntax.toFunctionalBuilderOps
import play.api.libs.json.{JsPath, Reads}

case class DiscordResponse(
                            commandName: String,
                            commandDescription: String,
                            args: Option[List[DiscordCommandArgs]]
                          ) {
  override def toString: String = {
    s"""
       |nome: $commandName
       |descrição: $commandDescription
       |$argsToString
       |""".stripMargin
  }

  private val argsToString: String = {
    args match {
      case Some(value) =>
        s"""
          |argumentos: ${value.map(_.toString).mkString("\n")}
          |""".stripMargin
      case None => ""
    }
  }
}

object DiscordResponse {
  implicit val reader: Reads[DiscordResponse] = {
    (
      (JsPath \ "command_name").read[String] and
      (JsPath \ "command_description").read[String] and
      (JsPath \ "args").readNullable[List[DiscordCommandArgs]]
    )(DiscordResponse.apply _)
  }
}

case class DiscordCommandArgs(
                               arg: String,
                               description: String
                             )

object DiscordCommandArgs {
  implicit val reader: Reads[DiscordCommandArgs] = {
    (
      (JsPath \ "arg").read[String] and
      (JsPath \ "description").read[String]
    )(DiscordCommandArgs.apply _)
  }
}