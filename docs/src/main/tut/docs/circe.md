---
layout: docs
title:  "Circe"
section: "docs"
---

# Circe

[Circe](https://circe.github.io/circe/) is _A JSON library for Scala powered by Cats_.

## Dependency

You have to explicitly enable circe as described in the documentation.

```scala
val circeVersion = "x.y.z"

libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)
```

## Usage

`Scala Mandrill` doesn't provide any codes as everything can be derived by circe at compile time.
To enable the automatic derivation import the following packages:

```tut:book:silent
import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
```

This example shows how to encode and decode a `SendTemplate` request and the `SendTemplateResponse`.

```tut:book:silent
import net.gutefrage.mandrill._
import net.gutefrage.mandrill.core._

import shapeless.Unwrapped
implicit def writeAnyVal[W <: AnyVal, U](implicit unwrapped: Unwrapped.Aux[W, U],
                                         encoderUnwrapped: Encoder[U]): Encoder[W] = {
  Encoder.instance[W](v => encoderUnwrapped(unwrapped.unwrap(v)))
}

import org.joda.time.DateTime
implicit val jodaDateTimeEncoder: Encoder[DateTime] = Encoder.instance(dt => dt.toString("yyyy-MM-dd HH:mm:ss").asJson)

val apiKey = MandrillApiKey("your-api-key")
val sendTemplateBody = Mandrill(apiKey).
    messages.
    sendTemplate("my-template-slug").
    to("foo@bar.com")
```

In order to encode this request simply call

```tut:book
// sendTemplateBody.asJson.spaces4

apiKey.asJson.spaces4
sendTemplateBody.send_at.asJson
sendTemplateBody.template_name.asJson
sendTemplateBody.template_content.asJson
sendTemplateBody.message.asJson
```
