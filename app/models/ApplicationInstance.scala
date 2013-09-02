package models

import play.api.db.slick.Config.driver.simple._

case class ApplicationInstance(id: Option[Guid], applicationId: Guid, applicationVersionId: Guid, hostId: Guid, status: Int)

object ApplicationInstances extends Table[ApplicationInstance]("ApplicationInstances") {
  def id = column[Option[Guid]]("id", O.PrimaryKey)

  def applicationId = column[Guid]("applicationId")

  def applicationVersionId = column[Guid]("applicationVersionId")

  def hostId = column[Guid]("hostId")

  def status = column[Int]("status")

  def * = id ~ applicationId ~ applicationVersionId ~ hostId ~ status <>(ApplicationInstance, ApplicationInstance.unapply _)

  def application = foreignKey("FK_ApplicationInstances_Applications", applicationId, Applications)(_.id.get)

  def applicationVersion = foreignKey("FK_ApplicationInstances_ApplicationVersions", applicationVersionId, ApplicationVersions)(_.id)

  def host = foreignKey("FK_ApplicationInstances_Hosts", hostId, Hosts)(_.id)
}
