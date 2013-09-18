package controllers

import models.{ApplicationInstances, ApplicationInstance}

object ApplicationInstanceController extends BaseController[ApplicationInstance, ApplicationInstances.type] {
  implicit val table = ApplicationInstances
  implicit val jsonFormat = ApplicationInstances.jsonFormat
}
