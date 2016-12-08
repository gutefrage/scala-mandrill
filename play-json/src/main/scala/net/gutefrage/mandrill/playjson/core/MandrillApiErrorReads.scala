package net.gutefrage.mandrill.playjson.core

import net.gutefrage.mandrill.core.{MandrillApiError, MandrillApiErrorName}
import play.api.data.validation.ValidationError
import play.api.libs.json._

object MandrillApiErrorReads {
  implicit val apiErrorNameReads: Reads[MandrillApiErrorName] = Reads({
    case JsString(label) =>
      MandrillApiErrorName.enum.decode(label) match {
        case Right(field) => JsSuccess(field)
        case Left(error) =>
          JsError(s"Invalid value $label, must be one of ${error.validValues}")
      }
    case other => JsError(s"Invalid type $other, must be a string!")
  })

  implicit val apiErrorReads: Reads[MandrillApiError] = for {
    _ <- (JsPath \ "status").read[String].filter(ValidationError("status != error"))(_ == "error")
    msg <- (JsPath \ "message").read[String]
    error <- (JsPath \ "name").read[MandrillApiErrorName]
  } yield MandrillApiError(msg, error)
}
