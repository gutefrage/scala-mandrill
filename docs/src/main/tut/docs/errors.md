---
layout: docs
title:  "Errors"
section: "docs"
---

# Errors

Mandrill has a generic error format:

```json
{
    "status": "error",
    "code": 11,
    "name": "Unknown_Message",
    "message": "No message exists with the id 'McyuzyCS5M3bubeGPP-XVA'"
}
```

which is modelled in the `MandrillApiError` class. The `name` property is explicitly typed for
easy pattern matching.

```
import net.gutefrage.mandrill.core._
import net.gutefrage.mandrill.core.MandrillApiErrorName._

val error = MandrillApiError("invalid api key", Invalid_Key)

error match {
  case MandrillApiError(msg, Invalid_Key) => println(s"oops, wrong key: $msg")
  case MandrillApiError(msg, ValidationError) => println(s"you did something wrong: $msg")
  case MandrillApiError(msg, err) => println(s"$err : $msg")
}
```
