package controllers

import play.api.mvc.{Action, Controller}
import models.{Application => ApplicationModel, Guid, Applications}
import play.api.libs.json.Json
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import Applications.jsonFormat
import play.api.Play.current

object ApplicationController extends Controller {
  def get(id: Guid) = Action {
    DB.withSession { implicit session:slick.session.Session =>
      val result = Query(Applications).filter(_.id === id.bind).first()
      Ok(Json.toJson(result))
    }
  }

  def getAll = Action {
    DB.withSession { implicit session: slick.session.Session =>
      val results = Query(Applications).list
      Ok(Json.toJson(results))
    }
  }

  // TODO: Support partial update to an entity via POST?

  def post = Action(parse.json) { implicit request =>
    (request.body \ "name").asOpt[String].map { name =>
      val uuid = Guid()
      DB.withSession {implicit session: slick.session.Session =>
        val app = ApplicationModel(uuid, name)
        Applications.insert(app)
        Created(Json.toJson(app)).withHeaders(LOCATION -> routes.ApplicationController.put(uuid).url)
      }
    }.getOrElse {
      BadRequest("Missing parameter [name]")
    }
  }

  def put(id: Guid) = Action(parse.json) { implicit request =>
    DB.withSession { implicit session: slick.session.Session =>
      val appQuery = Query(Applications).filter(_.id === id.bind)
      appQuery.firstOption.map { app =>
        Json.fromJson[ApplicationModel](request.body).map { newApp =>
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
      Query(Applications).filter(_.id === id.bind).delete
      NoContent
    }
  }
}
