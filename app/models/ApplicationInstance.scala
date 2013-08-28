package models

import scala.slick.driver.H2Driver.simple._

case class ApplicationInstance(id: Guid, applicationId: Guid, applicationVersionId: Guid, hostId: Guid, status: Int)

object ApplicationInstances extends Table[ApplicationInstance]("ApplicationInstances") {
  def id = column[Guid]("id", O.PrimaryKey)

  def applicationId = column[Guid]("applicationId")

  def applicationVersionId = column[Guid]("applicationVersionId")

  def hostId = column[Guid]("hostId")

  def status = column[Int]("status")

  def * = id ~ applicationId ~ applicationVersionId ~ hostId ~ status <>(ApplicationInstance, ApplicationInstance.unapply _)

  def application = foreignKey("FK_ApplicationInstances_Applications", applicationId, Applications)(_.id)

  def applicationVersion = foreignKey("FK_ApplicaitonInstances_ApplicationVersions", applicationVersionId, ApplicationVersions)(_.id)

  def host = foreignKey("FK_ApplicationInstances_Hosts", hostId, Hosts)(_.id)
}
