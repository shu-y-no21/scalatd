package controllers

import javax.inject._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

class ToDoController @Inject()(mcc: MessagesControllerComponents) extends MessagesAbstractController(mcc) {

  def hello() = Action {
      implicit request: MessagesRequest[AnyContent] => Ok("Hi, I'm Play :D")
  }

  def list() = Action {
    implicit request: MessagesRequest[AnyContent] => 
    val message: String = "display a list here :)"
    Ok(views.html.list(message))
  }

}