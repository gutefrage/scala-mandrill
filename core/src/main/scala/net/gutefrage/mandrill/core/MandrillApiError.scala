package net.gutefrage.mandrill.core

case class MandrillApiError(
  message: String,
  name: MandrillApiErrorName
)
