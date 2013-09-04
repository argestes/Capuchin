package models

import helpers.{BaseModel, BaseTable}
import play.api.libs.json.{Format, Json}

case class Application(
  id: Option[Guid],
  name: String,
  enabled: Option[Boolean]) extends BaseModel

object Applications extends BaseTable[Application]("Applications") {
  def name = column[String]("name")

  def enabled = column[Boolean]("enabled", O.Default(true))

  def * = id.? ~ name ~ enabled.? <>(Application, Application.unapply _)

  implicit val jsonFormat: Format[Application] = Json.format[Application]

  def prepareModel(m: Application) = m.copy(id = Some(Guid()), enabled = Some(true))
}

