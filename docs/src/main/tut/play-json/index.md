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
