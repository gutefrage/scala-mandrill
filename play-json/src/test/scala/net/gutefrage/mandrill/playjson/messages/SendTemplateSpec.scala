package net.gutefrage.mandrill.playjson.messages

import net.gutefrage.mandrill.messages.SendTemplate.{Message, TemplateContent}
import net.gutefrage.mandrill.messages.{MergeVar, Recipient, RecipientMergeVars, SendTemplate}
import net.gutefrage.mandrill.test.messages.arbitrary._
import org.scalatest.prop.PropertyChecks
import org.scalatest.{MustMatchers, WordSpec}
import play.api.libs.json.{JsArray, Json}

class SendTemplateSpec extends WordSpec with MustMatchers with PropertyChecks {

  "The SendTemplate Writes definition" must {

    "serialize a merge var" in { mergeVar: MergeVar =>
      Json.toJson(mergeVar) mustBe Json.obj(
        "name" -> mergeVar.name,
        "content" -> mergeVar.content
      )
    }

    "serialize a template content" in { templateContent: TemplateContent =>
      Json.toJson(templateContent) mustBe Json.obj(
        "name" -> templateContent.name,
        "content" -> templateContent.content
      )
    }

    "serialize a recipient" in { recipient: Recipient =>
      val json = Json.toJson(recipient)

      (json \ "email").as[String] mustBe recipient.email.value
      (json \ "name").asOpt[String] mustBe recipient.name.map(_.value)
      (json \ "type").asOpt[String] mustBe recipient.`type`.map(_.name)
    }

    "serialize a message" in { message: Message =>
      val json = Json.toJson(message)

      (json \ "subject").asOpt[String] mustBe message.subject.map(_.value)
    }

    "serialize recipient with merge vars" in { mergeVars: RecipientMergeVars =>
      val json = Json.toJson(mergeVars)

      (json \ "recipient" \ "email").as[String] mustBe mergeVars.recipient.email.value
      (json \ "merge_vars").as[JsArray].value must have size mergeVars.merge_vars.size
    }

    "serialize a SendTemplate" in {
      forAll { sendTemplate: SendTemplate =>
        val json = Json.toJson(sendTemplate)

        (json \ "key").as[String] mustBe sendTemplate.key.value
      }
    }
  }
}
