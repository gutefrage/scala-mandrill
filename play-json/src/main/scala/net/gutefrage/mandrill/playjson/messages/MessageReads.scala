package net.gutefrage.mandrill.playjson.messages

import net.gutefrage.mandrill.messages.SendTemplateResponse.{RejectReason, Status}
import net.gutefrage.mandrill.messages._
import play.api.libs.json._

object MessageReads {

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
