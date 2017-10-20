package ru.vk.web.action

import io.netty.handler.codec.http.HttpResponseStatus
import ru.vk.core.exception.UnauthorizedException
import xitrum.Action


case class ResponseDTO(status: Int = HttpResponseStatus.OK.code(), body: Option[Any] = None, error: Option[Any] = None)
trait AbstractRestAction extends Action {
  override def execute(): Unit = {
    try {
      val responseData = calculate()
      respondJson(responseData)
    } catch {
      case e: UnauthorizedException =>
        log.error(e.getMessage, e)
        response.setStatus(HttpResponseStatus.UNAUTHORIZED)
        respondJson(ResponseDTO(status = HttpResponseStatus.UNAUTHORIZED.code()))
      case e: Exception =>
        log.error(e.getMessage, e)
        response.setStatus(HttpResponseStatus.INTERNAL_SERVER_ERROR)
        respondJson(ResponseDTO(status = HttpResponseStatus.INTERNAL_SERVER_ERROR.code()))
    }

  }

  protected def calculate(): AnyRef
}