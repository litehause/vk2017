package ru.vk.core.model

import scala.slick.jdbc.GetResult

import scala.slick.jdbc.GetResult
import scala.slick.jdbc.StaticQuery.interpolation
import scala.slick.jdbc.JdbcBackend.Database._

/*CREATE TABLE exhibit (
id bigint primary key default nextval('nextval_slick'),
name VARCHAR(255) NOT NULL,
file BIGINT NOT NULL REFERENCES files(id),
thumbmail BIGINT REFERENCES files(id),
title VARCHAR(255),
information text
);*/

case class Exhibit(id: Long, name: String, fileId: Long, thumbMailFileId: Option[Long], title: String, information: Option[String])

object Exhibit {
  implicit val getResult = GetResult(r => Exhibit(r.<<, r.<<, r.<<, r.<<?, r.<<, r.<<?))

  def add(name: String, fileId: Long, thumbMailId: Option[Long], title: String, information: Option[String]) = {
    sql"""
      INSERT INTO exhibit(name, file, thumbmail, title, information)
      VALUES ($name, $fileId, $thumbMailId, $title, $information)
      returning id, name, file, thumbmail, title, information
    """.as[Exhibit].first
  }

  def update(id: Long, name: String, fileId: Long, thumbMailId: Option[Long], title: String, information: Option[String]) = {
    sql"""
      UPDATE exhibit
      SET name = $name, file = $fileId, thumbmail = $thumbMailId, title=$title, information = $information
      WHERE id = $id
    """.as[Exhibit].firstOption
  }

  def findById(id: Long) = {
    sql"""
      SELECT id, name, file, thumbmail, title, information
      FROM exhibit
      WHERE id = $id
    """.as[Exhibit].firstOption
  }

  def getList(limit: Long, offset: Long) = {
    sql"""
      SELECT id, name, file, thumbmail, title, information
      FROM exhibit
      ORDER BY id
      LIMIT $limit OFFSET $offset
    """.as[Exhibit].list
  }

  def getAll() = {
    sql"""
      SELECT id, name, file, thumbmail, title, information
      FROM exhibit
      ORDER BY id
    """.as[Exhibit].list
  }

  def count() = {
    sql"""
      SELECT count(*)
      FROM exhibit
    """.as[Long].first
  }
}
