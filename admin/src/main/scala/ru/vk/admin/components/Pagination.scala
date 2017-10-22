package ru.vk.admin.components

import xitrum.Action

class Pagination(count: Long, urlGenerator: (Int) => String, countOnPage: Int = 20)
                (implicit val action: Action) extends AbstractComponent {

  lazy val currentPage = action.paramo[Int]("page").map(_ - 1).getOrElse(0)

  lazy val offset = currentPage * countOnPage


  lazy val prev = Option(currentPage - 1).filter(_ >= 0).map(_ + 1)
  lazy val next = Option(currentPage + 1).filter(_ => (offset + countOnPage) < count).map(_ + 1)
  lazy val maxPages = (count.toDouble/countOnPage).ceil.toInt
  lazy val paginationStart = (currentPage - 2).max(1)
  lazy val paginationEnd = (currentPage + 3).min(maxPages)


  def render() = {
    at("currentPage") = currentPage
    at("next") = next
    at("prev") = prev
    at("start") = paginationStart
    at("end") = paginationEnd
    at("urlGenerator") = urlGenerator
    if (maxPages == 1) {
      ""
    } else {
      renderFragment("pagination")
    }
  }
}

object Pagination {
  val PAGE_PARAMETER = "page"
}
