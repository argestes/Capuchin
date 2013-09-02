package controllers

import play.api.mvc.{Action, Controller}
import models.{BaseTable, BaseModel, Guid}
import play.api.libs.json.Json
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import play.api.Play.current

trait CrudController[TModel <: BaseModel, TTable <: BaseTable[TModel]] extends Controller {
  implicit val table: TTable
  implicit val jsonFormat = table.jsonFormat

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
      val uuid = Guid()
      DB.withSession {implicit session: slick.session.Session =>
        table.insert(reqObj)
        Created(Json.toJson(reqObj)).withHeaders(LOCATION -> routes.ApplicationController.put(uuid).url)
      }
    }.getOrElse {
      BadRequest("Missing parameter [name]")
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
    DB.withSession {implicit session: slick.session.Session =>
      // TODO: Check if object exists?
      Query(table).filter(_.id === id.bind).delete
      NoContent
    }
  }
}
