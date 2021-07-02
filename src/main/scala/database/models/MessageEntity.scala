package database.models

import slick.jdbc.PostgresProfile.api._

class MessageEntity(tag: Tag) extends Table[(Int, String, String)](tag, "MESSAGE") {
  def id: Rep[Int] = column[Int]("MSG_ID", O.PrimaryKey, O.AutoInc)
  def message: Rep[String] = column[String]("MSG_MSG")
  def category: Rep[String] = column[String]("MSG_CAT")
  def * = (id, message, category)
}
