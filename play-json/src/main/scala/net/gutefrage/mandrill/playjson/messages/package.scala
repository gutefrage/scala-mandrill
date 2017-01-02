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

package net.gutefrage.mandrill.playjson

import net.gutefrage.mandrill.messages._
import net.gutefrage.mandrill.messages.SendTemplate.{Message, TemplateContent}
import net.gutefrage.mandrill.messages.SendTemplateResponse.{RejectReason, Status}
import net.gutefrage.mandrill.playjson.json.JsonWrites._
import play.api.libs.json._

/**
 * == Messages API JSON Formats ==
 *
 */
package object messages {

  // ----------------------------------
  // -------- Write instances ---------
  // ----------------------------------

  implicit val recipientTypeWrites: Writes[Recipient.RecipientType] =
    Writes[Recipient.RecipientType](t => JsString(t.name))

  implicit val recipientWrites: OWrites[Recipient] = Json.writes[Recipient]

  implicit val mergeVarWrites: Writes[MergeVar] = Json.writes[MergeVar]

  // minimal merge vars with recipients format
  implicit val recipientMergeVarsWrites: OWrites[RecipientMergeVars] = Json.writes[RecipientMergeVars]

  implicit val sendTemplateContent: OWrites[TemplateContent] = Json.writes[SendTemplate.TemplateContent]

  implicit val sendTemplateMessageWrites: OWrites[Message] = Json.writes[SendTemplate.Message]

  implicit val sendTemplateWrites: OWrites[SendTemplate] = Json.writes[SendTemplate]

  // ----------------------------------
  // -------- Read instances ----------
  // ----------------------------------

  implicit val sendTemplateResponseStatusReads: Reads[Status] = Reads({
    case JsString(label) =>
      SendTemplateResponse.statusEnum.values.find(_.status == label) match {
        case Some(field) => JsSuccess(field)
        case None =>
          val validValues = SendTemplateResponse.statusEnum.values.map(_.status)
          JsError(s"Invalid value $label, must be one of ${validValues mkString ", "}")
      }
    case other => JsError(s"Invalid type $other, must be a string!")
  })

  implicit val sendTemplateResponseRejectReasonReads: Reads[RejectReason] = Reads({
    case JsString(label) =>
      SendTemplateResponse.rejectReasonEnum.values.find(_.reason == label) match {
        case Some(field) => JsSuccess(field)
        case None =>
          val validValues = SendTemplateResponse.statusEnum.values.map(_.status)
          JsError(s"Invalid value $label, must be one of ${validValues mkString ", "}")
      }
    case other => JsError(s"Invalid type $other, must be a string!")
  })

  implicit val endTemplateResponseReads: Reads[SendTemplateResponse] = Json.reads[SendTemplateResponse]

}
