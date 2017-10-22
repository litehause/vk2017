package ru.vk.web

import ru.vk.core.db.DB
import xitrum.Server


object Boot {

  def main(args: Array[String]): Unit = {
    DB.db.createConnection().close()
    Server.start()
  }
}
