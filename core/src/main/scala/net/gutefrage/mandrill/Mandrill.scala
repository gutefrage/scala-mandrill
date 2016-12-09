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
