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

package net.gutefrage.mandrill

import net.gutefrage.mandrill.core.MandrillApiKey

/**
 * == Mandrill ==
 *
 * This class is the entry point for creating Mandrill API Requests.
 * Its structure mimics the REST API for easy cross referencing.
 *
 * == Usage ==
 *
 * Every `Mandrill` instance needs an API key.
 *
 * @example {{{
 *  import net.gutefrage.mandrill._
 *  import net.gutefrage.mandrill.core._
 *
 *  val apiKey = MandrillApiKey("your-api-key")
 *  val mandrill = Mandrill(apiKey)
 * }}}
 *
 * A concrete `sendTemplate` request can be created in a fluent style:
 *
 * @example {{{
 *    val sendTemplateBody = Mandrill(apiKey)
 *       .messages
 *       .sendTemplate("my-template-slug")
 *       .to("foo@bar.com")
 * }}}
 *
 * @see [[https://mandrillapp.com/api/docs/]]
 */
class Mandrill(apiKey: MandrillApiKey) {

  /**
   * Mandrill Messages API instance for creating requests.
   *
   * @return a new request generator instance
   */
  def messages(): Messages = new Messages(apiKey)

  /**
   * Mandrill Users API instance for creating requests.
   *
   * @return a new request generator instance
   */
  def users(): Users = new Users(apiKey)

}

object Mandrill {

  /**
   *
   * @param apiKey Mandrill API Key
   * @return a new API request generator instance
   */
  def apply(apiKey: MandrillApiKey): Mandrill = new Mandrill(apiKey)
}
