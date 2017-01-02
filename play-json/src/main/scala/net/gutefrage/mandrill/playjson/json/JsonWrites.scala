/*
 * Copyright 2016-2017 gutefrage.net GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
