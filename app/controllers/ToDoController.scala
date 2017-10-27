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

  val todoForm: Form[String] = Form("name" -> nonEmptyText)

  def todoNew = Action {  implicit request: MessagesRequest[AnyContent] =>
    Ok(views.html.createForm(todoForm))
  }

  def todoAdd() = Action {  implicit request: MessagesRequest[AnyContent] =>
    val name: String = todoForm.bindFromRequest().get
    //println(name)
    //Ok("Save")
    todoService.insert(ToDo(id = None, name))
    Redirect(routes.ToDoController.list())
  }

  def todoEdit(todoId: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    todoService.findById(todoId).map {  todo =>
      Ok(views.html.editForm(todoId, todoForm.fill(todo.name)))
    }.getOrElse(NotFound)
  }

  def todoUpdate(todoId: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    val name: String = todoForm.bindFromRequest().get
    todoService.update(todoId, ToDo(Some(todoId), name))
    Redirect(routes.ToDoController.list())
  }

  def todoDelete(todoId: Long) = Action { implicit request: MessagesRequest[AnyContent] =>
    todoService.delete(todoId)
    Redirect(routes.ToDoController.list())
  }

}