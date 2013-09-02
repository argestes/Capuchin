package models

import play.api.db.slick.Config.driver.simple._

case class Host(id: Guid, hostname: String)

object Hosts extends Table[Host]("Hosts") {
  def id = column[Guid]("id", O.PrimaryKey)

  def hostname = column[String]("hostname")

  def * = id ~ hostname <>(Host, Host.unapply _)
}