package net.gutefrage.mandrill.playjson.core

import net.gutefrage.mandrill.core.MandrillDateTime
import org.joda.time.format.DateTimeFormat
import play.api.libs.json.{JsString, Writes}

object MandrillDateTimeWrites {

  private val format = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss")
  implicit val mandrillDateTimeWrite: Writes[MandrillDateTime] =
    Writes[MandrillDateTime] { dateTime =>
      JsString(format.print(dateTime.value))
    }
}
