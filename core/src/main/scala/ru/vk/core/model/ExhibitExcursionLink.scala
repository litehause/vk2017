package ru.vk.core.model

import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.jdbc.JdbcBackend.Database._

case class ExhibitExcursionLink(exhibitId: Long, excursion: Long, index: Int)

object ExhibitExcursionLink {
  implicit val getResult = GetResult(r => ExhibitExhibitionLink(r.<<, r.<<, r.<<))

  def add(excursionId: Long, exhibitId: Long, index: Int) = {
    sql"""
      INSERT INTO exhibit_excursion_link(excursion_id, exhibit_id, index)
      VALUES ($excursionId, $exhibitId, $index)
      returning excursion_id, exhibit_id, index
    """.as[ExhibitExhibitionLink].first
  }

  def deleteByExhibitionId(excursionId: Long) = {
    sqlu"""
      DELETE FROM exhibit_excursion_link
      WHERE excursion_id = $excursionId
    """.execute
  }

  def findexhibitIdsByexhibition(excursionId: Long) = {
    sql"""
      SELECT exhibit_id
      FROM exhibit_excursion_link
      WHERE excursion_id = $excursionId
    """.as[Long].list
  }
}