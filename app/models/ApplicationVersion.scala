package models

import play.api.db.slick.Config.driver.simple._

case class ApplicationVersion(id: Guid, applicationId: Guid, name: String)

object ApplicationVersions extends Table[ApplicationVersion]("ApplicationVersions") {
  def id = column[Guid]("id", O.PrimaryKey)

  def applicationId = column[Guid]("applicationId")

  def name = column[String]("name")

  def * = id ~ applicationId ~ name <>(ApplicationVersion, ApplicationVersion.unapply _)

  def application = foreignKey("FK_ApplicationVersions_Applications", applicationId, Applications)(_.id.get)
}

