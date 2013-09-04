package controllers

import play.api.mvc.{Action, Controller}

object UIController extends Controller{
      def index = Action{ Ok(views.html.index("Applications")) }
}
