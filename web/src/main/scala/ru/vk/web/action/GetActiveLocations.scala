package ru.vk.web.action

import ru.vk.core.db.DBConnection
import ru.vk.core.model.Location
import xitrum.annotation.{GET, Swagger}


@GET("/location/get")
@Swagger(
  Swagger.Description("Получение координат"),
  Swagger.Response(200, "ok")
)
class GetActiveLocations extends AbstractRestAction
  with DBConnection
  with SwaggerLocationTag {
  override protected def calculate(): AnyRef = {
    db withDynSession Location.findAllLocations()
  }
}
