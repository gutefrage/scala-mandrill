---
layout: docs
title:  "Send Template"
section: "docs"
source: "core/src/main/scala/net/gutefrage/mandrill/Messages.scala"
scaladoc: "#net.gutefrage.mandrill.Messages"
---

# Send Template

Send a new transactional message through Mandrill using a template.

[Mandrill API Documentation](https://mandrillapp.com/api/docs/messages.JSON.html#method=send-template)

## Usage

Sending a template requires a `SendTemplate` instance. 

```tut:book:silent
import net.gutefrage.mandrill._
import net.gutefrage.mandrill.core._

val apiKey = MandrillApiKey("your-api-key")
val sendTemplateBody = Mandrill(apiKey).
    messages.
    sendTemplate("my-template-slug").
    to("foo@bar.com")
```

The `sendTemplateBody` has the required shape for the Mandrill API. Serialize with your own
serializer or with one of the supplied by `Scala Mandrill` and send the json to the required
REST endpoint. Mandrill returns either a [generic error](errors.html) or `SendTemplateResponse`.
gain you can provide your own serializer or use the ones provided by `Scala Mandrill`.

```tut:book:silent
import net.gutefrage.mandrill.messages._
def callSendTemplate(sendTemplate: SendTemplate): Either[MandrillApiError, SendTemplateResponse] = {
   // httpClient.post("...", Json.toJson(sendTemplate))
   ???
}
```

