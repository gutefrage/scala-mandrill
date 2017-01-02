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

import net.gutefrage.mandrill.core._
import org.joda.time.format.DateTimeFormat
import play.api.data.validation.ValidationError
import play.api.libs.json._

/**
 * == Mandrill core API Formats ==
 *
 */
package object core {

  // ----------------------------------
  // -------- Write instances ---------
  // ----------------------------------

  private val format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
  implicit val mandrillDateTimeWrite: Writes[MandrillDateTime] =
    Writes[MandrillDateTime] { dateTime =>
      JsString(format.print(dateTime.value))
    }

  // ----------------------------------
  // -------- Read instances ----------
  // ----------------------------------

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
