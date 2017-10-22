package ru.vk.core.model

import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.jdbc.JdbcBackend.Database._

case class Excursion(id: Long, title: String, information: String, imageId: Long, file: Long, exhibitionId: Long)

object Excursion {
  implicit val getResult = GetResult(r => Excursion(r.<<, r.<<, r.<<, r.<<, r.<<, r.<<))

  def add(title: String, information: String, imageId: Long, fileId: Long, exhibitionId: Long) = {
    sql"""
      INSERT INTO excursion(title, information, image, file, exhibition_id)
      VALUES ($title, $information, $imageId, $fileId, $exhibitionId)
      returning id, title, information, image, file, exhibition_id
    """.as[Excursion].first
  }

  def update(id: Long, title: String, information: String, imageId: Long, fileId: Long, exhibitionId: Long) = {
    sql"""
      UPDATE excursion
      SET title = $title, information = $information, image = $imageId, file = $fileId, exhibition_id = $exhibitionId
      WHERE id = $id
      returning id, title, information, image, file, exhibition_id
    """.as[Excursion].firstOption
  }

  def findAll() = {
    sql"""
      SELECT id, title, information, image, file, exhibition_id
      FROM excursion
    """.as[Excursion].list
  }

  def findById(id: Long) = {
    sql"""
      SELECT id, title, information, image, file, exhibition_id
      FROM excursion
      WHERE id = $id
    """.as[Excursion].firstOption
  }

}
