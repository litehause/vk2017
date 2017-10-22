package ru.vk.admin.action.exhibit

import ru.vk.admin.action.DefaultLayout
import ru.vk.core.db.DBConnection
import ru.vk.core.exception.RedirectException
import ru.vk.core.model.{DataFile, Exhibit}
import xitrum.SkipCsrfCheck
import xitrum.annotation.{GET, POST}

@GET("/exhibit/edit", "/exhibit/edit/:id<[0-9]+>")
@POST("/exhibit/edit", "/exhibit/edit/:id<[0-9]+>")
class ExhibitEdit extends DefaultLayout with DBConnection with SkipCsrfCheck {
  override def calculateRenderPage(): Unit = {

    val title = paramo("title")
      .map(_.trim)
      .filter(_.nonEmpty)
    val information = paramo("information")
      .map(_.trim)
      .filter(_.nonEmpty)
    val fileId = paramo("fileId")
      .map(_.trim)
      .filter(_.nonEmpty)
      .map(_.toLong)
    val name = paramo("name")
      .map(_.trim)
      .filter(_.nonEmpty)
    val thumbMailFileId = paramo("thumbMailFileId")
      .map(_.trim)
      .filter(_.nonEmpty)
      .map(_.toLong)

    (title, information, fileId, name, thumbMailFileId) match {
      case (Some(title), information, Some(fileId), Some(name), thumbmailId) =>
        save(paramo[Long]("id"), title = title, information = information, fileId = fileId, name = name, thumbMailId = thumbmailId)
      case _ =>
    }
    val editedExhibit = paramo[Long]("id").flatMap(id => db withDynSession Exhibit.findById(id))
    at("exhibit") = editedExhibit
    at("file") = editedExhibit.flatMap(ex => db withDynSession DataFile.findById(ex.fileId))
    at("thumbMail") = editedExhibit.flatMap(_.thumbMailFileId).flatMap(id => db withDynSession DataFile.findById(id))

  }


  private def save(id: Option[Long], title: String, information: Option[String], fileId: Long, name: String, thumbMailId: Option[Long]) = {
    id match {
      case Some(id) =>
        db withDynTransaction Exhibit.update(id, name = name,
          fileId = fileId, thumbMailId = thumbMailId,
          title = title, information = information)
      case None =>
        val exhibit = db withDynTransaction Exhibit.add(name = name, fileId = fileId, thumbMailId = thumbMailId, title = title, information = information)
        throw RedirectException(url[ExhibitEdit]("id" -> exhibit.id))
    }
  }
}
