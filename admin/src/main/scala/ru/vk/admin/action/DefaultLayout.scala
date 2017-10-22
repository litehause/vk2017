package ru.vk.admin.action

import ru.vk.core.exception.RedirectException
import xitrum.Action


trait DefaultLayout extends Action {
  override def layout = renderViewNoLayout[DefaultLayout]()

  override def execute(): Unit = {
    try {
      calculateRenderPage()
      respondView()
    } catch {
      case e: RedirectException =>
        redirectTo(e.url)
      case e: Exception =>
        log.error(e.getMessage, e)
        respond500Page()
    }
  }


  def calculateRenderPage(): Unit
}
