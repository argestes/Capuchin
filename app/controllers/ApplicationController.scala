package controllers

import models.{Application => ApplicationModel, Applications}

object ApplicationController extends CrudController[ApplicationModel, Applications.type] {
  implicit val table = Applications
}
