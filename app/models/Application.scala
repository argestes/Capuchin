package models

import play.api.db.slick.Config.driver.simple._
import play.api.libs.json.Json

case class Application(id: Option[Guid], name: String) extends BaseModel

object Applications extends BaseTable[Application]("Applications") {
  def name = column[String]("name")

  def * = id ~ name <>(Application, Application.unapply _)

  implicit val jsonFormat = Json.format[Application]
}

