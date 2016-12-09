package net.gutefrage.mandrill.users

import net.gutefrage.mandrill.core.MandrillApiKey

/**
 * == Ping ==
 *
 * Validate an API key and respond to a ping
 */
case class Ping(key: MandrillApiKey)
