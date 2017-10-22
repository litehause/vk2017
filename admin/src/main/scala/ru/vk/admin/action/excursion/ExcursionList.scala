package ru.vk.admin.action.excursion

import ru.vk.admin.action.DefaultLayout
import ru.vk.core.db.DBConnection
import ru.vk.core.model.Excursion
import xitrum.annotation.GET

@GET("/excursion")
class ExcursionList extends DefaultLayout with DBConnection {
  override def calculateRenderPage(): Unit = {
    at("excursions") = db withDynSession Excursion.findAll()
  }
}
