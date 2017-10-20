package ru.vk.core.config

import java.io.File

import com.typesafe.config.ConfigFactory

object Configuration {
  lazy val config = ConfigFactory.parseFile(new File(System.getenv("CONFIGURATION_PATH")))
}