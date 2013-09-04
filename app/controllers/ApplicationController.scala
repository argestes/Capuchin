package controllers

import models.{Application => ApplicationModel, Applications}

object ApplicationController extends BaseController[ApplicationModel, Applications.type] {
  implicit val table = Applications
  implicit val jsonFormat = Applications.jsonFormat
}
