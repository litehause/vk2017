package ru.vk.core.model

import java.sql.Timestamp

import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.jdbc.JdbcBackend.Database._
import scala.slick.jdbc.{ StaticQuery => Q }

case class User(id: Long,
                vkId: String,
                name: Option[String],
                gender: Option[String],
                isGuide: Boolean)


object User extends ActiveRecord {
  implicit val getResult = GetResult(r => User(r.<<, r.<<, r.<<?, r.<<?, r.<<))

  def add(vkId: String, name: Option[String], gender: Option[String]) = {
    sql"""
      INSERT INTO users(vkId, name, gender)
      VALUES ($vkId, $name, $gender)
      returning id, vkId, name, gender, isGuide
    """.as[User].first
  }

  def findById(id: Long): Option[User] = {
    sql"""
      SELECT id, vkId, name, gender, isGuide
      FROM users
      WHERE id = $id
    """.as[User].firstOption
  }

  def findByVKId(vkId: String): Option[User] = {
    sql"""
      SELECT id, vkId, name, gender, isGuide
      FROM users
      WHERE vkId = $vkId
    """.as[User].firstOption
  }

  def findByIds(ids: List[Long]): List[User] = {
    if (ids.size == 0) {
      Nil
    } else {
      val in = ("?," * ids.size).dropRight(1)
      Q.query[List[Long], User](s"""
        SELECT id, vkId, name, gender, isGuide
        FROM users
        WHERE id in ($in)
      """).apply(ids).list
    }
  }
}
