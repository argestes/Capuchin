package models

import scala.slick.lifted.{BaseTypeMapper, MappedTypeMapper, TypeMapper}
import java.util.UUID
import play.api.libs.json._
import play.api.mvc.PathBindable.Parsing

/*
 * Simple wrapper for java.util.UUID so we can nicely convert it
 */
case class Guid(id: UUID = UUID.randomUUID()) {
  override def toString = id.toString
}

object Guid {
  def fromString(s: String) = Guid(UUID.fromString(s))

  // Magic for parsing guid in the request
  implicit object pathBindableGuid extends Parsing[Guid](
    fromString,
    _.toString,
    (key: String, e: Exception) => "Cannot parse parameter %s as Guid: %s".format(key, e.getMessage)
  )

  // JSON Formatter for Guid
  implicit object GuidFormat extends Format[Guid] {
    def reads(json: JsValue): JsResult[Guid] = JsSuccess(Guid(UUID.fromString(json.as[String])))

    def writes(o: Guid): JsValue = Json.toJson(o.toString)
  }

  // Type mapper for Slick
  implicit object GuidMapper extends MappedTypeMapper[Guid, UUID] with BaseTypeMapper[Guid] {
    def map(t: Guid): UUID = t.id

    def comap(u: UUID): Guid = new Guid(u)
  }
}
