package ru.vk.core.model

import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.jdbc.JdbcBackend.Database._

case class DataFile(id: Long, name: String, dtype: String)

object DataFile {

  val DTYPE_exhibit_thumb_mail = "DTYPE_exhibit_thumb_mail"
  val DTYPE_exhibit_file = "DTYPE_exhibit_thumb_fail"
  val DTYPE_exhibition_image = "DTYPE_exhibition_image"
  val DTYPE_excursion   = "DTYPE_excursion"
  val DTYPE_excursion_image = "DTYPE_excursion_image"

  implicit val getResult = GetResult(r=> DataFile(r.<<, r.<<, r.<<))

  def add(name: String, dtype: String) = {
    sql"""
      INSERT INTO files(name, dtype)
      VALUES ($name, $dtype)
      returning id, name, dtype
    """.as[DataFile].first
  }

  def findById(id: Long) = {
    sql"""
      SELECT id, name, dtype FROM files
      WHERE id = $id
    """.as[DataFile].firstOption
  }

}