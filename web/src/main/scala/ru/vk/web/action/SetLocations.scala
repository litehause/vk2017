package ru.vk.web.action

import ru.vk.core.db.DBConnection
import ru.vk.core.model.Location
import xitrum.SkipCsrfCheck
import xitrum.annotation.{POST, Swagger}

@POST("/location/set")
@Swagger(
  Swagger.Tags("Установка новых координат"),
  Swagger.Description("Установка новых координат"),
  Swagger.StringQuery("coordinate", "координаты в json которые будут сохранены как нужно"),
  Swagger.Response(200, "ok")
)
class SetLocations extends AbstractRestAction
  with DBConnection with UserAction with SkipCsrfCheck {
  override protected def calculate(): AnyRef = {
    val route = param("coordinate")
    db withDynTransaction Location.save(userId = user.id,
      coordinate = route)
  }
}
