package net.gutefrage.mandrill.messages

import enum.Enum

/**
 * == Send-Template Response ==
 *
 * Response from the `/messages/send-template.json` endpoint.
 *
 * @param _id Mandrill id for the performed action
 * @param email Receiver email
 * @param status Status of the sent email
 * @param rejectReason Optional reject reason if the email could not be sent
 */
final case class SendTemplateResponse(
  _id: String,
  email: String,
  status: SendTemplateResponse.Status,
  rejectReason: Option[SendTemplateResponse.RejectReason]
)

object SendTemplateResponse {
  sealed abstract class Status(val status: String)

  object Send extends Status("sent")
  object Scheduled extends Status("scheduled")
  object Queued extends Status("queued")
  object Rejected extends Status("rejected")
  object Invalid extends Status("invalid")

  val statusEnum: Enum[Status] = Enum.derived[Status]

  sealed abstract class RejectReason(val reason: String)

  object Rule extends RejectReason("rule")
  object Spam extends RejectReason("spam")
  object Unsubscribed extends RejectReason("unsub")
  object Custom extends RejectReason("custom")
  object HardBounce extends RejectReason("hard-bounce")
  object SoftBounce extends RejectReason("soft-bounce")
  object InvalidSender extends RejectReason("invalid-sender")
  object TestModeLimit extends RejectReason("test-mode-limit")

  val rejectReasonEnum: Enum[RejectReason] = Enum.derived[RejectReason]

}
