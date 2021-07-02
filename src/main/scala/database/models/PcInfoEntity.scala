package database.models

import slick.jdbc.PostgresProfile.api._

class PcInfoEntity(tag: Tag) extends Table[(String, String, String, String, String)](tag, "PCINFO") {
  def name: Rep[String] = column[String]("NAME")
  def cpu: Rep[String] = column[String]("CPU")
  def gpu: Rep[String] = column[String]("GPU")
  def ram: Rep[String] = column[String]("RAM")
  def motherboard: Rep[String] = column[String]("MOTHERBOARD")
  def * = (name, cpu, gpu, ram, motherboard)
}
