package models

import helpers.{BaseTable, BaseModel}
import play.api.libs.json.{Format, Json}

case class Host(id: Guid = Guid(), hostname: String) extends BaseModel

object Hosts extends BaseTable[Host]("Hosts") {
  def hostname = column[String]("hostname")

  def * = id ~ hostname <>(Host, Host.unapply _)

  implicit val jsonFormat: Format[Host] = Json.format[Host]
}