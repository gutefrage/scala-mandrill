---
layout: docs
title:  "Ping"
section: "docs"
source: "core/src/main/scala/net/gutefrage/mandrill/Users.scala"
scaladoc: "#net.gutefrage.mandrill.Users"
---

# Ping2

Validate an API key and respond to a ping

[Mandrill API Documentation](https://mandrillapp.com/api/docs/users.JSON.html#method=ping2)

## Usage

Sending a template requires a `Ping` instance. 

```tut:book:silent
import net.gutefrage.mandrill._
import net.gutefrage.mandrill.core._

val apiKey = MandrillApiKey("your-api-key")
val ping = Mandrill(apiKey).users.ping
```

The `ping` has the required shape for the Mandrill API. Serialize with your own
serializer or with one of the supplied by `Scala Mandrill` and send the json to the required
REST endpoint. Mandrill returns either a [generic error](errors.html) or `PingResponse`.
gain you can provide your own serializer or use the ones provided by `Scala Mandrill`.

```tut:book:silent
import net.gutefrage.mandrill.users._
def callSendTemplate(ping: Ping): Either[MandrillApiError, Pong] = {
   // httpClient.post("...", Json.toJson(sendTemplate))
   ???
}
```

