package models

import helpers.{BaseModel, BaseTable}
import play.api.libs.json.{Format, Json}

case class ApplicationInstance(
  id: Option[Guid],
  applicationId: Guid,
  applicationVersionId: Guid,
  hostId: Guid,
  status: Int) extends BaseModel

object ApplicationInstances extends BaseTable[ApplicationInstance]("ApplicationInstances") {
  def applicationId = column[Guid]("applicationId")

  def applicationVersionId = column[Guid]("applicationVersionId")

  def hostId = column[Guid]("hostId")

  def status = column[Int]("status")

  def * = id.? ~ applicationId ~ applicationVersionId ~ hostId ~ status <>(ApplicationInstance, ApplicationInstance.unapply _)

  def application = foreignKey("FK_ApplicationInstances_Applications", applicationId, Applications)(_.id)

  def applicationVersion = foreignKey("FK_ApplicationInstances_ApplicationVersions", applicationVersionId, ApplicationVersions)(_.id)

  def host = foreignKey("FK_ApplicationInstances_Hosts", hostId, Hosts)(_.id)

  implicit val jsonFormat: Format[ApplicationInstance] = Json.format[ApplicationInstance]

  def prepareModel(m: ApplicationInstance) = m.copy(id = Some(Guid()))
}
