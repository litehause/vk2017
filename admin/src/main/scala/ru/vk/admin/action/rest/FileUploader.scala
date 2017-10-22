package ru.vk.admin.action.rest

import java.io.File
import java.nio.file.{Files, Path}

import io.netty.handler.codec.http.multipart.FileUpload
import ru.vk.core.config.GlobalConstants
import ru.vk.core.db.DBConnection
import ru.vk.core.model.DataFile
import xitrum.{Action, SkipCsrfCheck}
import xitrum.annotation.{GET, POST}

@POST("/rest/fileUploader")
class FileUploader extends Action
  with SkipCsrfCheck with DBConnection {
  override def execute(): Unit = {
    val dtype = param[String]("dtype")
    val fileParametr = param[FileUpload]("file")
    val folder = getFolder(dtype)
    val extension = getExtensionByFileName(fileParametr.getFilename)
    val newFileName = generateFileName(extension)
    createFolderIsNotExists(folder)

    Files.copy(fileParametr.getFile().toPath, new File(folder + newFileName).toPath)
    val resultFile = db withDynTransaction DataFile.add(newFileName, dtype)
    respondJson(resultFile)
  }

  def getExtensionByFileName(fileName: String) = {
    fileName.substring(fileName.lastIndexOf('.') + 1)
  }

  def generateFileName(extension: String) = {
    java.util.UUID.randomUUID().toString.replaceAll("-", "") + "." + extension
  }


  private def createFolderIsNotExists(path: String): Unit = {
    val folder = new File(path)
    if(!folder.exists()) {
      folder.mkdir()
    }
  }

  def getFolder(dtype: String) = {
    GlobalConstants.fileDirectory + dtype + "/"
  }
}
