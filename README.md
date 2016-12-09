# Minimal Scala API for Mandrill

[Mandrill](http://mandrill.com/) is a transactional email API for MailChimp users.
This library provides a API models and json (de)serializers.

## Goals

There is already a scala library scamandrill](https://github.com/dzsessona/scamandrill).
While this library as a lot more API models implemented it takes a more opinionated approach.
 
`Scala Mandrill` provides

- Generic API models and provides parsers for different json libs. You choose.
- No Mandrill Email Client. It's up to the user which http library to use

In addition the API models are incomplete. We'll add more as the library evolves.


## Features

- `core` provides API models and helpers to create them
- `play-json` provides json `Reads` and `Writes` instances for all models
- `test-kit` provides scalacheck generators and arbitraries for all API models

## Supported APIs

- [users](https://mandrillapp.com/api/docs/users.JSON.html)
  - [ping](https://mandrillapp.com/api/docs/users.JSON.html#method=ping)
  - [ping2](https://mandrillapp.com/api/docs/users.JSON.html#method=ping2)
- [messages](https://mandrillapp.com/api/docs/messages.JSON.html)
  - [send-template](https://mandrillapp.com/api/docs/messages.JSON.html#method=send-template)

## Usage

Mandrill API request bodies are constructed via a fluent interface.

```scala
import net.gutefrage.mandrill._
import net.gutefrage.mandrill.core._

val apiKey = MandrillApiKey("your-api-key")
val sendTemplateBody = Mandrill(apiKey)
      .messages
      .sendTemplate("my-template-slug")
      .to("foo@bar.com")
```

For more information read [the documentation](https://gutefrage.github.io/scala-mandrill)
