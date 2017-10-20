package ru.vk.core.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import scala.slick.jdbc.JdbcBackend.Database
import ru.vk.core.config.DBConfiguration

object DB {
  def dsHikary = {
    val config = new HikariConfig()
    config.setDataSourceClassName(DBConfiguration.driverClassName)
    config.addDataSourceProperty("serverName", DBConfiguration.host)
    config.addDataSourceProperty("databaseName", DBConfiguration.dbName)
    config.addDataSourceProperty("user", DBConfiguration.user)
    config.addDataSourceProperty("password", DBConfiguration.password)
    config.setMaximumPoolSize(DBConfiguration.maxPoolSize)
    config.setMinimumIdle(DBConfiguration.minimumIdle)
    config.setIdleTimeout(5000)
    config.setConnectionTimeout(1000)
    config.setValidationTimeout(1000)
    new HikariDataSource(config)
  }

  val db = Database.forDataSource(dsHikary)
}


trait DBConnection {
  implicit def db = DB.db
}