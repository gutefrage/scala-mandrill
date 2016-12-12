---
layout: docs
title:  "Play JSON"
section: "docs"
---

# Play JSON

The `play-json` sub module provides `Reads` and `Writes` instances for all API models.

## Dependency

In your `build.sbt` add the module to the library dependencies.

```scala
libraryDependencies += "net.gutefrage.mandrill" %% "play-json" % "<version>"
```
## Usage

Convert a `send-template` request to json requires the following steps.
First import all necessary classes and instances.

```tut:book:silent
import net.gutefrage.mandrill._
import net.gutefrage.mandrill.core._
import net.gutefrage.mandrill.playjson.messages._
import play.api.libs.json.Json
```

Next, create a `send-template` request:

```tut:book:silent
val apiKey = MandrillApiKey("your-api-key")
val sendTemplateBody = Mandrill(apiKey).
    messages.
    sendTemplate("my-template-slug").
    to("foo@bar.com")
```

This request can be serialized to json with

```tut:book
val json = Json.toJson(sendTemplateBody)
Json.prettyPrint(json)
```

## Email Service with WSClient

The playframework provides [a http client](https://www.playframework.com/documentation/2.5.x/ScalaWS) that
works well together with play-json. This example doesn't require a play application.


### Email Service interface

The service can be used to send templates and check the API connection.

```tut:book:silent
import scala.concurrent.Future
import net.gutefrage.mandrill.messages._

trait EmailService {

  def status(): Future[Either[MandrillApiError, Unit]]
  
  def send(template: SendTemplate): Future[Either[MandrillApiError, Seq[SendTemplateResponse]]]
}

```

### Prolog


First, you need to add `play-ws` as a dependency with

```scala
libraryDependency += "com.typesafe.play" %% "play-ws" % "2.5.10"
```

Next we need to create a `WSClient`. In a play application you can inject it. For a standalone version
you have to create an instance of `AhcWSClient`:


```tut:book:silent
import akka.actor.ActorSystem 
import akka.stream.{ActorMaterializer, Materializer} 
import play.api.libs.ws._
import play.api.libs.ws.ahc.AhcWSClient

import scala.concurrent.ExecutionContext.Implicits._

implicit val system = ActorSystem()
implicit val materializer = ActorMaterializer()
val wsClient = AhcWSClient()
```

### Implementation

Now we can implement our `EmailService` trait. The implementation is very basic and doesn't contain all the
necessary error handling, but gives you an idea how to roll your own email service.

```tut:book:silent
import net.gutefrage.mandrill.playjson.core._
import net.gutefrage.mandrill.playjson.messages._
import net.gutefrage.mandrill.playjson.users._
import play.api.libs.json._

class WsEmailService(ws: WSClient, apiKey: MandrillApiKey) extends EmailService  {

  val api = "https://mandrillapp.com/api/1.0"

  def status(): Future[Either[MandrillApiError, Unit]] = {
      ws.url(s"$api/users/ping2.json").post(
        Json.toJson(Mandrill(apiKey).users.ping)
      ).map {
        case resp if resp.status <= 200 && resp.status <= 300 => Right(Unit)
        case resp => Left(parseApiError(resp))
      }
  }
  
  def send(template: SendTemplate): Future[Either[MandrillApiError, Seq[SendTemplateResponse]]] = {
     ws.url(s"$api/messages/send-template.json").post(
       Json.toJson(template)
     ).map {
       case resp if resp.status <= 200 && resp.status <= 300 => 
          // No invalid json error handling
          val responses = Json.fromJson[Seq[SendTemplateResponse]](resp.json).get
          Right(responses)
       case resp => Left(parseApiError(resp))
     }
  }
  
  // No error handling for invalid json
  private def parseApiError[T](resp: WSResponse): MandrillApiError = Json.fromJson[MandrillApiError](resp.json).get

}
```

### Usage

A short demonstration.

```tut:book
import scala.concurrent.Await, scala.concurrent.duration._

val service = new WsEmailService(wsClient, apiKey)
Await.result(service.status(), 5.seconds)

val template = Mandrill(apiKey).messages.sendTemplate("my-template").to("test@example.com")
Await.result(service.send(template), 5.seconds)

```

And don't forget to shutdown the clients.

```tut:book:silent
wsClient.close()
system.terminate()

```
