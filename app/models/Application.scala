package models

import play.api.db.slick.Config.driver.simple._
import play.api.libs.json.Json

case class Application(id: Guid, name: String)

object Applications extends Table[Application]("Applications") {
  def id = column[Guid]("id", O.PrimaryKey)

  def name = column[String]("name")

  def * = id ~ name <>(Application, Application.unapply _)

  implicit val jsonFormat = Json.format[Application]
}
