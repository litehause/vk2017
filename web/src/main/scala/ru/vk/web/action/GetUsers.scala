package ru.vk.web.action

import ru.vk.core.db.DBConnection
import ru.vk.core.model.User
import xitrum.SkipCsrfCheck
import xitrum.annotation.{GET, POST, Swagger}


@POST("/users/get")
@Swagger(
  Swagger.Tags("users"),
  Swagger.Description("Получение координат"),
  Swagger.StringBody("ids")
)
class GetUsers extends AbstractRestAction
  with DBConnection with SkipCsrfCheck {
  override protected def calculate(): AnyRef = {
    val userIds = bodyTextParams.getOrElse("ids", Seq.empty).map(_.toLong)
    db withDynSession User.findByIds(userIds.toList)
  }
}
