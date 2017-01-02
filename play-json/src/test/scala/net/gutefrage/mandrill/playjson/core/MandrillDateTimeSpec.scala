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
