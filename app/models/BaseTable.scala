package models

import play.api.db.slick.Config.driver.simple._
import play.api.libs.json.Json

abstract class BaseTable[TModel <: BaseModel](table: String) extends Table[TModel](table) {
  def id = column[Option[Guid]]("id", O.PrimaryKey)

  implicit val jsonFormat: play.api.libs.json.Format[TModel]
}

