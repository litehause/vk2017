package ru.vk.core.config


object DBConfiguration {

  lazy val user = "pg_vk2017"

  lazy val password = "dtlmvf"

  lazy val dbName = "vk2017"

  lazy val host = "127.0.0.1"

  val driverClassName = "org.postgresql.ds.PGSimpleDataSource"

  val maxPoolSize = 10

  val minimumIdle = 5
}