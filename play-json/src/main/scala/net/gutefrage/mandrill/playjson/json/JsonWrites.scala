package net.gutefrage.mandrill.playjson.json

import play.api.libs.json.Writes
import shapeless.Unwrapped

/**
 * == Json Writes ==
 *
 * Helper to derivce play-json Writes.
 *
 * @author Sebastian Wiesner
 */
object JsonWrites {

  /**
   * Writes instance for a wrapper type.
   *
   * The instance unwraps the value of the wrapper type and then uses the Writes instance for the unwrapped type to
   * convert to JSON.
   *
   * @param unwrapped The Unwrapped instance which supports unwrapping the type
   * @param writesUnwrapped The Writes instance for the unwrapped type
   * @tparam W The wrapper type
   * @tparam U The unwrapped type
   * @return A Writes instance for the wrapper type W
   */
  implicit def writeAnyVal[W <: AnyVal, U](implicit unwrapped: Unwrapped.Aux[W, U],
                                           writesUnwrapped: Writes[U]): Writes[W] =
    Writes(v => writesUnwrapped.writes(unwrapped.unwrap(v)))

}
