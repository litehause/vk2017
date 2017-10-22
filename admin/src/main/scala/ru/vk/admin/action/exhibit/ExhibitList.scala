package ru.vk.admin.action.exhibit

import ru.vk.admin.action.DefaultLayout
import ru.vk.admin.components.Pagination
import ru.vk.core.db.DBConnection
import ru.vk.core.model.Exhibit
import xitrum.annotation.GET

@GET("/exhibit")
class ExhibitList extends DefaultLayout with DBConnection {
  override def calculateRenderPage(): Unit = {
    val count = db withDynSession Exhibit.count()
    def urlGenerator(page: Int): String = {
      url[ExhibitList](Pagination.PAGE_PARAMETER -> page)
    }
    val pagination = new Pagination(count = count,
      urlGenerator = urlGenerator, countOnPage = 20)
    at("exhibits") = db withDynSession Exhibit.getList(20, pagination.offset)
    at("pagination") = pagination
  }
}
