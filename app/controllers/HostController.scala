package controllers

import models.{Host, Hosts}

object HostController extends BaseController[Host, Hosts.type]{
  implicit val table = Hosts
  implicit val jsonFormat = Hosts.jsonFormat
}
