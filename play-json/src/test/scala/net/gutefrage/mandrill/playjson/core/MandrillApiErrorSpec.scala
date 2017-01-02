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
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, OptionValues, WordSpec}
import play.api.libs.json._

class MandrillApiErrorSpec extends WordSpec with MustMatchers with OptionValues with PropertyChecks {

  "The MandrillApiErrorSpec Reads instance" must {

    val mandrillErrors = Table[String, MandrillApiErrorName](
      ("Error", "Model"),
      ("Invalid_Key", MandrillApiErrorName.Invalid_Key),
      ("ValidationError", MandrillApiErrorName.ValidationError),
      ("GeneralError", MandrillApiErrorName.GeneralError),
      ("Unknown_Subaccount", MandrillApiErrorName.Unknown_Subaccount),
      ("Unknown_Template", MandrillApiErrorName.Unknown_Template),
      ("PaymentRequired", MandrillApiErrorName.PaymentRequired)
    )

    val apiErrorNameGen: Gen[String] = Gen.oneOf(MandrillApiErrorName.enum.labels.toSeq)

    "deserialize all known API error names" in {
      forAll(apiErrorNameGen) { apiErrorName =>
        JsString(apiErrorName).as[MandrillApiErrorName] mustBe MandrillApiErrorName.enum.decodeOpt(apiErrorName).value
      }
    }

    "deserialize all known API errors" in {
      forAll(mandrillErrors) { (errorKey, apiError) =>
        val message = Arbitrary.arbitrary[String].sample.get
        val error = Json.obj(
          "status" -> "error",
          "message" -> message,
          "name" -> errorKey
        )
        error.as[MandrillApiError] mustBe MandrillApiError(message, apiError)
      }
    }

    "not deserialize a response" when {
      "the status is not 'error'" in {
        val error = Json.obj(
          "status" -> "info"
        )
        checkSingleValidationError(error.validate[MandrillApiError], JsPath \ "status", "status != error")
      }

      "the message field is missing" in {
        val error = Json.obj(
          "status" -> "error"
        )
        checkSingleValidationError(error.validate[MandrillApiError], JsPath \ "message", "error.path.missing")
      }

      "the name field is missing" in {
        forAll { message: String =>
          val error = Json.obj(
            "status" -> "error",
            "message" -> message
          )
          checkSingleValidationError(error.validate[MandrillApiError], JsPath \ "name", "error.path.missing")
        }
      }
    }
  }

  private def checkSingleValidationError[A](result: JsResult[A], path: JsPath, errorMsg: String): Unit = {
    result mustBe a[JsError]

    val JsError(jsErrors) = result
    jsErrors must have size 1
    jsErrors.head._1 mustBe path

    val validationErrors = jsErrors.head._2
    validationErrors must have size 1
    validationErrors.head.message mustBe errorMsg
  }
}
