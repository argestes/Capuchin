package controllers

import play.api.mvc.Controller
import models.{Application => ApplicationModel, Guid, Applications}
import play.api.libs.json.Json
import play.api.db.slick.Config.driver.simple._
import play.api.db.slick._
import Applications.jsonFormat
import play.api.Play.current

object ApplicationController extends Controller {
  def get(id: Guid) = DBAction { implicit requestWithSession =>
    val results = Query(Applications).filter(_.id === id).list
    Ok(Json.toJson(results))
  }

  def getAll = DBAction {implicit requestWithSession =>
    val results = Query(Applications).list
    Ok(Json.toJson(results))
  }

  def post = DBAction { implicit requestWithSession =>
    requestWithSession.request.body.asJson.map { json =>
      (json \ "name").asOpt[String].map { name =>
        val uuid = Guid()
        val app = ApplicationModel(uuid, name)
        Applications.insert(app)
        val json = Json.toJson(app)
        Ok(json)
      }.getOrElse {
        BadRequest("Missing parameter [name]")
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }

  def put(id: Guid) = DBAction { implicit requestWithSession =>
    val appQuery = Query(Applications).filter(_.id === id)
    appQuery.firstOption.map { app =>
      requestWithSession.request.body.asJson.map { json =>
        Json.fromJson[ApplicationModel](json).map { newApp =>
          if (app.id == newApp.id) {
            appQuery.update(newApp)
          } else {
            BadRequest("ID mismatch")
          }
        }
      }.getOrElse {
        BadRequest("Expecting Json data")
      }
    }.getOrElse {
      NotFound("Application not found")
    }
    ???
  }

  def delete(id: Guid) = DBAction { implicit requestWithSession =>
    // TODO: Check if object exists?
    Query(Applications).filter(_.id === id).delete
    Ok(Json.toJson("Deleted"))
  }
}
