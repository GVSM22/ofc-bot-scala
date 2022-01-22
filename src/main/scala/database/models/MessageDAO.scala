package database.models

import slick.jdbc.PostgresProfile.api._

class MessageDAO(tag: Tag) extends Table[(Int, String, String)](tag, "message") {
  def id: Rep[Int] = column[Int]("msg_id", O.PrimaryKey, O.AutoInc)
  def message: Rep[String] = column[String]("msg_msg")
  def category: Rep[String] = column[String]("msg_cat")
  def * = (id, message, category)
}
