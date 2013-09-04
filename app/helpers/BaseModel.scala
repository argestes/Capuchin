package helpers

import models.Guid

trait BaseModel {
  val id: Option[Guid]
}
