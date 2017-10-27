package services

import javax.inject.Inject

import anorm.SqlParser._
import anorm._
import play.api.db.DBApi

import scala.language.postfixOps

case class ToDo(name: String)

@javax.inject.Singleton
class ToDoService @Inject() (dbapi: DBApi) {

  private val db = dbapi.database("default")

  val simple = {
    get[String]("todo.name") map {
      case name => ToDo(name)
    }
  }

  def list(): Seq[ToDo] = {

    db.withConnection { implicit connection =>

      SQL(
        """
          select * from todo
        """
      ).as(simple *)

    }

  }
  
}