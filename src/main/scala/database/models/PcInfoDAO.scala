package database.models

import slick.jdbc.PostgresProfile.api._

class PcInfoDAO(tag: Tag) extends Table[(String, String, String, String, String)](tag, "pc_info") {
  def name: Rep[String] = column[String]("name")
  def cpu: Rep[String] = column[String]("cpu")
  def gpu: Rep[String] = column[String]("gpu")
  def ram: Rep[String] = column[String]("ram")
  def motherboard: Rep[String] = column[String]("motherboard")
  def * = (name, cpu, gpu, ram, motherboard)
}
