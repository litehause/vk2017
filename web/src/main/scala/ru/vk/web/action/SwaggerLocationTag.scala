package ru.vk.web.action

import xitrum.annotation.Swagger

@Swagger(
  Swagger.Tags("Location")
)
trait SwaggerLocationTag {
  this: AbstractRestAction =>
}
