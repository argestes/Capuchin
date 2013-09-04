package controllers

import play.api.mvc.{EssentialAction, Action, Controller}
import play.api.libs.json.{Format, Json}
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import play.api.Play.current
import helpers.{BaseModel, BaseTable}
import models.Guid

trait CrudController[Id] extends Controller {
  def get(id: Id): EssentialAction
  def getAll: EssentialAction
  def post: EssentialAction
  def put(id: Id): EssentialAction
  def delete(id: Id): EssentialAction
}

trait BaseController[TModel <: BaseModel, TTable <: BaseTable[TModel]]
  extends CrudController[Guid]
{

  implicit val table: TTable
  implicit val jsonFormat: Format[TModel]

  def get(id: Guid) = Action {
    DB.withSession { implicit session:slick.session.Session =>
      val result = Query(table).filter(_.id === id.bind).first()
      Ok(Json.toJson(result))
    }
  }

  def getAll = Action {
    DB.withSession { implicit session: slick.session.Session =>
      val results = Query(table).list
      Ok(Json.toJson(results))
    }
  }

  // TODO: Support partial update to an entity via POST?

  def post = Action(parse.json) { implicit request =>
    Json.fromJson[TModel](request.body).map { reqObj =>
      DB.withSession {implicit session: slick.session.Session =>
        val objToInsert = table.prepareModel(reqObj)
        table.insert(objToInsert)
        // FIXME: Location header
        Created(Json.toJson(objToInsert)).withHeaders(LOCATION -> "FIXME")
      }
    }.getOrElse {
      BadRequest("Can't map request to an object")
    }
  }

  def put(id: Guid) = Action(parse.json) { implicit request =>
    DB.withSession { implicit session: slick.session.Session =>
      val appQuery = Query(table).filter(_.id === id.bind)
      appQuery.firstOption.map { app =>
        Json.fromJson[TModel](request.body).map { newApp =>
          if (app.id == newApp.id) {
            appQuery.update(newApp)
            Ok(Json.toJson(newApp))
          } else {
            BadRequest("ID mismatch")
          }
        }.getOrElse {
          BadRequest("Application object expected")
        }
      }.getOrElse {
          NotFound("Application not found")
      }
    }
  }

  def delete(id: Guid) = Action {
    DB.withSession { implicit session: slick.session.Session =>
      // TODO: Check if object exists?
      Query(table).filter(_.id === id.bind).delete
      NoContent
    }
  }
}
