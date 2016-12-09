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

```tut:silent
import net.gutefrage.mandrill._
import net.gutefrage.mandrill.core._

val apiKey = MandrillApiKey("your-api-key")
val sendTemplateBody = Mandrill(apiKey).
    messages.
    sendTemplate("my-template-slug").
    to("foo@bar.com")
```
