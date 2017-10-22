package ru.vk.admin.action.excursion

import ru.vk.admin.action.DefaultLayout
import ru.vk.core.db.DBConnection
import ru.vk.core.exception.RedirectException
import ru.vk.core.model._
import xitrum.annotation.GET


@GET("/excursion/edit/:id<[0-9]+>", "/excursion/edit/")
class ExcursionEdit extends DefaultLayout with DBConnection {
  override def calculateRenderPage(): Unit = {
    val excursionId = paramo[Long]("id")

    val title = paramo("title").map(_.trim).filter(_.nonEmpty)
    val information = paramo("information").map(_.trim).filter(_.nonEmpty)
    val fileId = paramo("fileId").map(_.trim).filter(_.nonEmpty).map(_.toLong)
    val imageId = paramo("imageId").map(_.trim).filter(_.nonEmpty).map(_.toLong)
    val exhibition = paramo("exhibition").map(_.trim).filter(_.nonEmpty).map(_.toLong)
    val exhibits = params[Long]("exhibits[]")

    (title, information, fileId, imageId, exhibition, exhibits) match {
      case (Some(title), Some(information), Some(fileId), Some(imageId), Some(exbition), exhibits) if exhibits.nonEmpty =>
        save(excursionId, title, information, fileId, imageId, exbition, exhibits)
      case _ =>
    }

    val excursion = excursionId.flatMap(ex => db withDynSession Excursion.findById(ex))

    at("excursion") = excursion
    at("exhibitions") = db withDynSession Exhibition.getAll()
    at("exhibits") = db withDynSession Exhibit.getAll()
    at("file") = excursion.flatMap(ex => db withDynSession DataFile.findById(ex.file))
    at("image") = excursion.flatMap(ex => db withDynSession DataFile.findById(ex.imageId))
    at("selectedExhibits") = excursion
      .map(ex => db withDynSession ExhibitExcursionLink.findexhibitIdsByexhibition(ex.id))
      .getOrElse(List.empty)
  }

  private def save(id: Option[Long], title: String, information: String,
                   fileId: Long, imageId: Long, exbition: Long, exhibits: Seq[Long]) = {
    id match {
      case Some(id) =>
        db withDynTransaction {
          Excursion.update(id, title, information, imageId, fileId, exbition)
          ExhibitExcursionLink.deleteByExhibitionId(id)
          exhibits.foldLeft(0) { case (result, item)=>
            ExhibitExcursionLink.add(id, item, result)
            result + 1
          }
        }
      case _ =>
        val excursion = db withDynTransaction {
          val ex = Excursion.add(title, information, imageId, fileId, exhibitionId = exbition)
          exhibits.foldLeft(0) { case (result, item)=>
            ExhibitExcursionLink.add(ex.id, item, result)
            result + 1
          }
          ex
        }
        throw RedirectException(url[ExcursionEdit]("id"->excursion.id))
    }
  }
}
