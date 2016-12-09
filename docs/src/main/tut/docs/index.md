---
layout: docs
title:  "Getting started"
section: "docs"
---


# Getting Started

The [Mandrill API documentation](https://mandrillapp.com/api/docs/) structures their endpoints in multiple
categories. Scala mandrill follows this structure in its API. 

## Creating requests

The core feature of Scala Mandrill is to create request entities that you can serialize to json and
send to the mandrill api. For every request you need a `MandrillApiKey`.

```tut:book:silent
import net.gutefrage.mandrill._
import net.gutefrage.mandrill.core._

val apiKey = MandrillApiKey("your-api-key")
```

With this API key you can construct a `Mandrill` instance.

```tut:book:silent
val mandrill = Mandrill(apiKey)
```

The `Mandrill` instance provides instances for each Mandrill API category. For example the
[Messages Calls](https://mandrillapp.com/api/docs/messages.JSON.html) endpoint:

```tut:book:silent
val sendTemplateBody = mandrill.
  // the Messages API
  messages.
  // the actual endpoint
  sendTemplate("my-template-slug").
  // configure the endpoint
  to("foo@bar.com")
```

## Serialize requests

The Mandrill API expects a JSON body. Scala Mandrill provides json serializers for the following json libraries

- [play-json]({{ site.baseurl }}/play-json.html)
