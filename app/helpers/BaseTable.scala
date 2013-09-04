package helpers

import play.api.db.slick.Config.driver.simple._
import models.Guid

abstract class BaseTable[TModel <: BaseModel](table: String) extends Table[TModel](table) {
  def id = column[Guid]("id", O.PrimaryKey)

  implicit val jsonFormat: play.api.libs.json.Format[TModel]
}

