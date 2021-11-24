package database

import database.models.MessageDAO
import slick.jdbc.PostgresProfile.api._

trait DatabaseLayer {

  val db: Database = Database.forConfig("db")
  val messages = TableQuery[MessageDAO]
}
