package ru.vk.web.action

import io.netty.handler.codec.http.DefaultCookie
import ru.vk.core.config.GlobalConstants
import ru.vk.core.db.DBConnection
import ru.vk.core.model.User
import ru.vk.core.service.SessionService
import xitrum.annotation.{GET, Swagger}

case class ResponseAuthorization(id: Long,
                                 session: String,
                                 vkId: String,
                                 gender: Option[String],
                                 name: Option[String],
                                 isGuide: Boolean)

@GET("/authorize")
@Swagger(
  Swagger.Description("Авторизация или регистрация нового пользователя в ответ отдасться кука все последущие запросы необходимо производить с ней"),
  Swagger.Description("Авторизация или регистрация нового пользователя в ответ отдасться кука все последущие запросы необходимо производить с ней"),
  Swagger.StringQuery("id", "id пользователя в контакте"),
  Swagger.StringQuery("name", "Имя пользователя например иван иванов или костя сергеев"),
  Swagger.StringQuery("gender", "пол M или G"),
  Swagger.Response(200, "ok")
)
class AuthorizeAction extends AbstractRestAction with DBConnection {
  override protected def calculate(): AnyRef = {
    val vkId = param[String]("id")
    val name = param[String]("name")
    val gender = param[String]("gender")

    val user = db withDynTransaction User.findByVKId(vkId)
      .getOrElse(User.add(vkId, Some(name), Some(gender)))
    val session = SessionService.getSessionByUser(user)

    val cookie = new DefaultCookie(GlobalConstants.sessionId, session)
    cookie.setPath("/")
    cookie.setHttpOnly(false)
    responseCookies.append(cookie)
    ResponseAuthorization(user.id, session, user.vkId, user.gender, user.name, user.isGuide)
  }
}
