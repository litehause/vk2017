package ru.vk.core.service

import java.net.InetSocketAddress

import net.spy.memcached.MemcachedClient
import ru.vk.core.config.MemcachedConfiguration

import scala.util.Try

object MemcachedService {
  private val memcached: MemcachedClient = new MemcachedClient(
    new InetSocketAddress(MemcachedConfiguration.MEMCACHED_HOST, MemcachedConfiguration.MEMCACHED_PORT))


  def getByKey(key: String): Option[String] = {
    Option(memcached.get(key)).flatMap { value =>
      Try(value.toString).toOption
    }
  }

  def set(key:String, value: String) = {
    memcached.set(key, MemcachedConfiguration.EXPERIANSE, value)
  }
}