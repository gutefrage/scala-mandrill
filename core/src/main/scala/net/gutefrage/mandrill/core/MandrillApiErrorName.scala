package net.gutefrage.mandrill.core

import enum.Enum

/**
 * == Mandrill API Error ==
 *
 * Mandrill has a fixed format for API errors, which is represented by this case object structure
 */
sealed trait MandrillApiErrorName

object MandrillApiErrorName {

  // common errors
  case object Invalid_Key extends MandrillApiErrorName
  case object ValidationError extends MandrillApiErrorName
  case object GeneralError extends MandrillApiErrorName
  case object Unknown_Subaccount extends MandrillApiErrorName

  // messages errors
  case object Unknown_Template extends MandrillApiErrorName
  case object PaymentRequired extends MandrillApiErrorName

  val enum: Enum[MandrillApiErrorName] = Enum.derived[MandrillApiErrorName]
}
