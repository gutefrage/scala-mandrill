package net.gutefrage.mandrill.playjson.core

import net.gutefrage.mandrill.core._
import org.scalatest.{MustMatchers, OptionValues, WordSpec}
import org.scalatest.prop.PropertyChecks
import play.api.libs.json._

class MandrillDateTimeSpec extends WordSpec with MustMatchers with OptionValues with PropertyChecks {

  "The MandrillApiDateTime Writes instance" must {
    "serialize a valid date time" in { dateTime: MandrillDateTime =>
      Json.toJson(dateTime).as[String] mustBe dateTime.value.toString("yyyy-MM-dd HH:mm:ss")
    }
  }

}
