package ru.vk.core.model

import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.jdbc.JdbcBackend.Database._

case class Exhibition(id: Long, title: String, information: String, imageId: Long)

object Exhibition {
  implicit val getResult = GetResult(r => Exhibition(r.<<, r.<<, r.<<, r.<<))

  def add(title: String, information: String, image: Long): Exhibition = {
    sql"""
      INSERT INTO exhibition(title, information, image)
      VALUES ($title, $information, $image)
      returning id, title, information, image
    """.as[Exhibition].first
  }

  def update(id: Long, title: String, information: String, image: Long) = {
    sql"""
      UPDATE exhibition
      SET title = $title, information = $information, image = $image
      WHERE id = $id
      returning id, title, information, image
    """.as[Exhibition].firstOption
  }

  def findById(id: Long) = {
    sql"""
      SELECT id, title, information, image
      FROM exhibition
      WHERE id = $id
    """.as[Exhibition].firstOption
  }

  def count() = {
    sql"""
         SELECT count(*)
         FROM exhibition
    """.as[Long].first
  }

  def get(limit: Long, offset: Long) = {
    sql"""
      SELECT id, title, information, image
      FROM exhibition
      LIMIT $limit OFFSET $offset
    """.as[Exhibition].list
  }

  def getAll() = {
    sql"""
      SELECT id, title, information, image
      FROM exhibition
    """.as[Exhibition].list
  }
}