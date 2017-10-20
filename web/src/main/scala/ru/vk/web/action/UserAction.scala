package ru.vk.web.action

import ru.vk.core.config.GlobalConstants
import ru.vk.core.exception.UnauthorizedException
import ru.vk.core.model.User
import ru.vk.core.service.SessionService
import xitrum.Action


trait UserAction {
  this: Action =>

  lazy val user: User = {
    val session = requestCookies.get(GlobalConstants.sessionId)
    session.flatMap(SessionService.getUserBySession)
        .getOrElse(throw UnauthorizedException())
  }
}
