package controllers

import models.{ApplicationVersion, ApplicationVersions}

object ApplicationVersionController extends BaseController[ApplicationVersion, ApplicationVersions.type]{
  implicit val table = ApplicationVersions
  implicit val jsonFormat = ApplicationVersions.jsonFormat
}
