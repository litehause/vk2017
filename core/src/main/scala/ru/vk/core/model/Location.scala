package ru.vk.core.model

import java.sql.Timestamp

import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.jdbc.JdbcBackend.Database._

case class Location(userId: Long,
                    coordinate: String,
                    lastModification: Timestamp)

object Location {
  implicit val getResult = GetResult(r => Location(r.<<, r.<<, r.<<))


  def findAllLocations() = {
    sql"""
       select user_id, coordinate, last_Modification
       from location
    """.as[Location].list
  }

  def save(userId: Long, coordinate: String) = {
    update(userId = userId, coordinate = coordinate)
        .getOrElse(add(userId = userId, coordinate = coordinate))
  }

  private def update(userId: Long, coordinate: String) = {
    sql"""
      UPDATE location
      SET coordinate = $coordinate, last_Modification = now()
      WHERE user_id = $userId
      returning user_id, coordinate, last_Modification
    """.as[Location].firstOption
  }

  private def add(userId: Long, coordinate: String) = {
    sql"""
      INSERT INTO location(user_id, coordinate, last_Modification)
      VALUES ($userId, $coordinate, now())
      returning user_id, coordinate, last_Modification
    """.as[Location].first
  }
}