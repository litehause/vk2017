package ru.vk.core.model

import scala.slick.jdbc.SetParameter
import scala.collection.Set
import scala.slick.jdbc.StaticQuery

class ActiveRecord {

  val SQL_PARAMETER = "?"

  def generateParametersIn(size: Int) = (s"$SQL_PARAMETER," * size).dropRight(1)

  def seqParam[A](implicit pconv: SetParameter[A]): SetParameter[Seq[A]] = SetParameter {
    case (seq, pp) =>
      for (a <- seq) {
        pconv.apply(a, pp)
      }
  }

  def setParam[A](implicit pconv: SetParameter[A]): SetParameter[Set[A]] = SetParameter {
    case (seq, pp) =>
      for (a <- seq) {
        pconv.apply(a, pp)
      }
  }

  def addOrder[P, R](field: String, staticQuery: StaticQuery[P, R]) = {
    staticQuery + s" order by $field"
  }

  def addLimitOffset[P, R](limit: Int, offset: Int, staticQuery: StaticQuery[P, R]) = {
    staticQuery + " limit " +? limit + " offset " +? offset
  }

  implicit def setLongList = seqParam[Long]
  implicit def setStringSet = setParam[String]

  def escapeLike(query: String) = {
    query.replaceAll("%", "\\%").replaceAll("_", "\\_")
  }
}