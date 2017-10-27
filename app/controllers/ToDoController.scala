package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

// services
import services._

class ToDoController @Inject()(todoService: ToDoService, mcc: MessagesControllerComponents) extends MessagesAbstractController(mcc) {

  def hello() = Action {
      implicit request: MessagesRequest[AnyContent] => Ok("Hi, I'm Play :D")
  }

  /*
  def list() = Action {
    implicit request: MessagesRequest[AnyContent] =>
    val items: Seq[ToDo] = Seq(ToDo("ToDo1"), ToDo("ToDo2"))
    Ok(views.html.list(items))
  }
  */

  def list() = Action { implicit request: MessagesRequest[AnyContent] =>
    val items: Seq[ToDo] = todoService.list()
    Ok(views.html.list(items))
  }

}