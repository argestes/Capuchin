package models

import helpers.{BaseModel, BaseTable}
import play.api.libs.json.{Format, Json}

case class ApplicationVersion(
  id: Option[Guid],
  applicationId: Guid,
  name: String) extends BaseModel

object ApplicationVersions extends BaseTable[ApplicationVersion]("ApplicationVersions") {
  def applicationId = column[Guid]("applicationId")

  def name = column[String]("name")

  def * = id.? ~ applicationId ~ name <>(ApplicationVersion, ApplicationVersion.unapply _)

  def application = foreignKey("FK_ApplicationVersions_Applications", applicationId, Applications)(_.id)

  implicit val jsonFormat: Format[ApplicationVersion] = Json.format[ApplicationVersion]

  def prepareModel(m: ApplicationVersion) = m.copy(id = Some(Guid()))
}
