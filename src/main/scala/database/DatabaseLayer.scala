package database

import database.models.MessageDAO
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import liquibase.{Contexts, Liquibase}
import slick.jdbc.PostgresProfile.api._

import java.sql.Connection

trait DatabaseLayer {

  val db: Database = Database.forConfig("db")
  val messages = TableQuery[MessageDAO]

  private val changeLogFile = "migrations/db.changelog-master.yaml"

  private def createLiquibase(dbConnection: Connection, changeLogFilePath: String): Liquibase = {
    val database = DatabaseFactory.getInstance.findCorrectDatabaseImplementation(new JdbcConnection(dbConnection))
    val classLoader = classOf[DatabaseLayer].getClassLoader
    val resourceAccessor = new ClassLoaderResourceAccessor(classLoader)
    new Liquibase(changeLogFilePath, resourceAccessor, database)
  }

  private def updateDb(dbConnection: Connection, changeLogFilePath: String): Unit = {
    val liquibase = createLiquibase(dbConnection, changeLogFilePath)
    try {
      liquibase.update(null: Contexts)
    } catch {
      case e: Throwable => println(e.getMessage)
    } finally {
      liquibase.forceReleaseLocks()
      dbConnection.rollback()
      dbConnection.close()
    }
  }

  def runMigrations(connection: Connection): Unit =
    updateDb(connection, changeLogFile)

}
