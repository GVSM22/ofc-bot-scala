package database

import database.models.MessageDAO
import liquibase.{Contexts, Liquibase}
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import slick.jdbc.PostgresProfile.api._

import java.sql.Connection

trait DatabaseLayer {

  val db: Database = Database.forConfig("db")
  val messages = TableQuery[MessageDAO]

  private val changeLogFile = "db/changelog/db.changelog-master.yaml"

  private def createLiquibase(dbConnection: Connection, diffFilePath: String): Liquibase = {
    val database = DatabaseFactory.getInstance.findCorrectDatabaseImplementation(new JdbcConnection(dbConnection))
    val classLoader = classOf[DatabaseLayer].getClassLoader
    val resourceAccessor = new ClassLoaderResourceAccessor(classLoader)
    new Liquibase(diffFilePath, resourceAccessor, database)
  }

  private def updateDb(dbConnection: Connection, diffFilePath: String): Unit = {
    val liquibase = createLiquibase(dbConnection, diffFilePath)
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
