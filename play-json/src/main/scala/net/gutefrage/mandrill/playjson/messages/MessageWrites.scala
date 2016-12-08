package net.gutefrage.mandrill.playjson.messages

import net.gutefrage.mandrill.messages._
import net.gutefrage.mandrill.messages.SendTemplate.{Message, TemplateContent}
import net.gutefrage.mandrill.playjson.core.MandrillDateTimeWrites._
import net.gutefrage.mandrill.playjson.json.JsonWrites._
import play.api.libs.json._

object MessageWrites {
  implicit val recipientTypeWrites: Writes[Recipient.RecipientType] =
    Writes[Recipient.RecipientType](t => JsString(t.name))

  implicit val recipientWrites: OWrites[Recipient] = Json.writes[Recipient]

  implicit val mergeVarWrites: Writes[MergeVar] = Json.writes[MergeVar]

  // minimal merge vars with recipients format
  implicit val recipientMergeVarsWrites: OWrites[RecipientMergeVars] = Json.writes[RecipientMergeVars]

  implicit val sendTemplateContent: OWrites[TemplateContent] = Json.writes[SendTemplate.TemplateContent]

  implicit val sendTemplateMessageWrites: OWrites[Message] = Json.writes[SendTemplate.Message]

  implicit val sendTemplateWrites: OWrites[SendTemplate] = Json.writes[SendTemplate]

}
