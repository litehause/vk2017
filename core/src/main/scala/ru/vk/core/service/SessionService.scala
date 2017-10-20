package ru.vk.core.service

import ru.vk.core.db.DBConnection
import ru.vk.core.dto.memcached.{SessionDataDTO, SessionUserDataDTO}
import ru.vk.core.model.User
import xitrum.util.SeriDeseri

import scala.util.Try

object SessionService extends DBConnection {

  private val KEY_PREFIX = "vk2017_"

  private def sessionKey(userId:Long) = {
    s"${KEY_PREFIX}sessiondata_${userId.toString}"
  }

  private def userData(session: String) = {
    s"${KEY_PREFIX}userdata_${session}"
  }

  def getUserBySession(session: String): Option[User] = {
    MemcachedService.getByKey(userData(session))
      .flatMap(data => Try(SeriDeseri.fromJson[SessionUserDataDTO](data)).toOption).flatten
      .map(_.userId).flatMap(userId => db withDynSession User.findById(userId))
  }

  def getSessionByUser(user: User): String = {
    MemcachedService.getByKey(sessionKey(user.id))
      .flatMap(data => Try(SeriDeseri.fromJson[SessionDataDTO](data)).toOption).flatten match {
      case Some(session) => session.session
      case None => createSession(user)
    }
  }

  private def createSession(user: User): String = {
    import com.roundeights.hasher.Implicits._
    import scala.language.postfixOps
    val session = s"""${user.id.toString}${System.nanoTime().toString}""".sha1.hex
    MemcachedService.set(userData(session), SeriDeseri.toJson(SessionUserDataDTO(user.id)))
    MemcachedService.set(sessionKey(user.id), SeriDeseri.toJson(SessionDataDTO(session)))
    session
  }
}