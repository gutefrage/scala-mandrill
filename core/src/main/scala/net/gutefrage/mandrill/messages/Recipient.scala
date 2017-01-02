/*
 * Copyright 2016-2017 gutefrage.net GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
