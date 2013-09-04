package helpers

import play.api.db.slick.Config.driver.simple._
import models.Guid

abstract class BaseTable[TModel <: BaseModel](table: String) extends Table[TModel](table) {
  def id = column[Guid]("id", O.PrimaryKey)

  /** Generates a new model to be inserted into the database */
  def prepareModel(m: TModel): TModel

  implicit val jsonFormat: play.api.libs.json.Format[TModel]
}

