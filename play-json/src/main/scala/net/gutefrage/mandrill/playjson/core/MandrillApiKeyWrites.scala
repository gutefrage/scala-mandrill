package net.gutefrage.mandrill.playjson.core

import net.gutefrage.mandrill.core.MandrillApiKey
import play.api.libs.json.{Json, OWrites}

trait MandrillApiKeyWrites {
  implicit val apiKeyWrites: OWrites[MandrillApiKey] =
    Json.writes[MandrillApiKey]
}
