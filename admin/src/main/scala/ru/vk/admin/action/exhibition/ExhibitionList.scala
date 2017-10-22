package ru.vk.admin.action.exhibition

import ru.vk.admin.action.DefaultLayout
import ru.vk.admin.components.Pagination
import ru.vk.core.db.DBConnection
import ru.vk.core.model.Exhibition
import xitrum.annotation.GET

@GET("exhibition/")
class ExhibitionList extends DefaultLayout with DBConnection {
  override def calculateRenderPage(): Unit = {
    val count = db withDynSession Exhibition.count()

    def urlGenerator(page: Int) = {
      url[ExhibitionList](Pagination.PAGE_PARAMETER -> page)
    }

    val pagination = new Pagination(count, urlGenerator)
    at("pagination") = pagination
    at("exhibition") = db withDynSession Exhibition.get(20, pagination.offset)
  }
}
