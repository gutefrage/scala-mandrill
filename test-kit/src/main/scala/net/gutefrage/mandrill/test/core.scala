package net.gutefrage.mandrill.test

import net.gutefrage.mandrill.core._
import org.joda.time.DateTime
import org.scalacheck.{Arbitrary, Gen}

object core {

  val apiKeyGen: Gen[MandrillApiKey] = Gen.identifier.map(MandrillApiKey)

  val apiErrorNameGen: Gen[MandrillApiErrorName] = Gen.oneOf(MandrillApiErrorName.enum.values.toSeq)

  val apiErrorGen: Gen[MandrillApiError] = for {
    message <- Gen.alphaNumStr
    errorName <- apiErrorNameGen
  } yield MandrillApiError(message, errorName)

  val mandrillDateTimeGen: Gen[MandrillDateTime] =
    Gen.posNum[Long].map(millis => MandrillDateTime(new DateTime(millis)))

  object arbitrary {
    implicit val arbitraryApiKey: Arbitrary[MandrillApiKey] = Arbitrary(apiKeyGen)
    implicit val arbitraryApiError: Arbitrary[MandrillApiError] = Arbitrary(apiErrorGen)
  }
}
