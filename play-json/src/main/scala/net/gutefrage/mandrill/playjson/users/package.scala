package net.gutefrage.mandrill.playjson

import net.gutefrage.mandrill.users._
import net.gutefrage.mandrill.playjson.json.JsonWrites._
import play.api.libs.json.Json

package object users {

  implicit val pingWrites = Json.writes[Ping]

  implicit val pongReads = Json.reads[Pong]

}
