package ru.vk.core.model

import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.jdbc.JdbcBackend.Database._

case class ExhibitExhibitionLink(exhibitionId: Long, exhibitId: Long, index: Int)

object ExhibitExhibitionLink {
  implicit val getResult = GetResult(r=> ExhibitExhibitionLink(r.<<, r.<<, r.<<))


  def add(exhibitionId: Long, exhibitId: Long, index: Int) = {
    sql"""
      INSERT INTO exhibit_exhibition_link(exhibition_id, exhibit_id, index)
      VALUES ($exhibitionId, $exhibitId, $index)
      returning exhibition_id, exhibit_id, index
    """.as[ExhibitExhibitionLink].first
  }

  def deleteByExhibitionId(exhibitionId: Long) = {
    sqlu"""
      DELETE FROM exhibit_exhibition_link
      WHERE exhibition_id = $exhibitionId
    """.execute
  }

  def findexhibitIdsByexhibition(exhibitionId: Long) = {
    sql"""
      SELECT exhibit_id
      FROM exhibit_exhibition_link
      WHERE exhibition_id = $exhibitionId
    """.as[Long].list
  }
}

/*CREATE TABLE exhibit_exhibition_link (
exhibition_id BIGINT NOT NULL REFERENCES exhibition(id),
exhibit_id BIGINT NOT NULL REFERENCES exhibit(id),
index int not NULL DEFAULT 0
);*/
