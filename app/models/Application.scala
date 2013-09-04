package models

import helpers.{BaseTable, BaseModel}
import play.api.libs.json.{Format, Json}

case class Application(id: Guid = Guid(), name: String, enabled: Boolean) extends BaseModel

object Applications extends BaseTable[Application]("Applications") {
  def name = column[String]("name")

  def enabled = column[Boolean]("enabled", O.Default(true))

  def * = id ~ name ~ enabled <>(Application, Application.unapply _)

  implicit val jsonFormat: Format[Application] = Json.format[Application]
}

