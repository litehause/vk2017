package ru.vk.admin.action.exhibition

import ru.vk.admin.action.DefaultLayout
import ru.vk.core.db.DBConnection
import ru.vk.core.exception.RedirectException
import ru.vk.core.model.{DataFile, Exhibit, ExhibitExhibitionLink, Exhibition}
import xitrum.SkipCsrfCheck
import xitrum.annotation.{GET, POST}

@GET("exhibition/edit/", "exhibition/edit/:id<[0-9]+>")
@POST("exhibition/edit/", "exhibition/edit/:id<[0-9]+>")
class ExhibitionEdit extends
  DefaultLayout with SkipCsrfCheck with DBConnection{
  override def calculateRenderPage(): Unit = {
    val exhibitionId = paramo[Long]("id")

    val title = paramo("title").map(_.trim).filter(_.nonEmpty)
    val information = paramo("information").map(_.trim).filter(_.nonEmpty)
    val fileId = paramo("fileId").map(_.trim).filter(_.nonEmpty).map(_.toLong)
    val exhibits = params[Long]("exhibits[]")

    (title, information, fileId, exhibits) match {
      case (Some(title), Some(information), Some(fileId), exhibts) if exhibits.nonEmpty =>
        save(exhibitionId, title = title, information= information, fileId, exhibits)
      case _ =>
    }

    val exhibition = exhibitionId.flatMap(id => db withDynSession Exhibition.findById(id))


    at("image") = exhibition.flatMap(ex => db withDynSession DataFile.findById(ex.imageId))
    at("exhibition") = exhibition
    at("exhibits") = db withDynSession Exhibit.getAll()
    at("selectedExhibits") = exhibition
      .map(ex => db withDynSession ExhibitExhibitionLink.findexhibitIdsByexhibition(ex.id))
      .getOrElse(List.empty)
  }


  private def save(id: Option[Long], title: String, information: String, imageId: Long, exhibts: Seq[Long]) = {
    id match {
      case Some(id) =>
        db withDynTransaction {
          Exhibition.update(id, title, information, imageId)
          ExhibitExhibitionLink.deleteByExhibitionId(id)
          exhibts.foldLeft(0) {case (index, item) =>
            ExhibitExhibitionLink.add(id, item, index)
            index + 1
          }
        }

      case _=>
        val ex = db withDynTransaction {
          val ex = Exhibition.add(title, information, imageId)
          exhibts.foldLeft(0) {case (index, item) =>
            ExhibitExhibitionLink.add(ex.id, item, index)
            index + 1
          }
          ex
        }
        throw RedirectException(url[ExhibitionEdit]("id" -> ex.id))
    }
  }
}
