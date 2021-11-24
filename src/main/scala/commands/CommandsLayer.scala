package commands

import bot.ConfigLayer

trait CommandsLayer { self: ConfigLayer =>
  val commands: Seq[CommandList] = Seq(
    new SimpleCommands(),
    new MessageCommands()
  )
}
