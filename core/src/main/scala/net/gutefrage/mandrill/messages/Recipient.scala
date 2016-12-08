package net.gutefrage.mandrill.messages

import enum.Enum
import Recipient._

/**
 *
 * @param email The email address of the recipient
 * @param name The optional display name to use for the recipient
 * @param `type` The header type to use for the recipient, defaults to "to" if not provided
 *               oneof(to, cc, bcc)
 */
final case class Recipient(email: Email, name: Option[Name] = None, `type`: Option[RecipientType] = None)

object Recipient {
  final case class Email(value: String) extends AnyVal
  final case class Name(value: String) extends AnyVal

  /**
   * == Mandrill Recipient Type ==
   *
   * The header type to use for the recipien
   */
  sealed abstract class RecipientType(val name: String)

  object RecipientType {
    case object To extends RecipientType("to")
    case object Cc extends RecipientType("cc")
    case object Bcc extends RecipientType("bcc")

    /**
     * Enumeration of all defined [[RecipientType]]s
     */
    val enum: Enum[RecipientType] = Enum.derived[RecipientType]
  }
}
