package net.gutefrage.mandrill

import net.gutefrage.mandrill.core.MandrillApiKey
import net.gutefrage.mandrill.users._

/**
 *
 * == Users API ==
 *
 * API for generating requests.
 *
 * @param apiKey
 */
class Users(apiKey: MandrillApiKey) {

  /**
   * @return request body for the ping and ping2 api endpoint
   */
  val ping: Ping = Ping(apiKey)

}
