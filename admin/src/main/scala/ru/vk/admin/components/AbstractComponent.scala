package ru.vk.admin.components

import xitrum.Action

abstract class AbstractComponent(implicit action: Action) extends Action {
  override def execute() = throw new Exception
}
