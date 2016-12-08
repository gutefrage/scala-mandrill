package net.gutefrage.mandrill

import net.gutefrage.mandrill.core.MandrillApiKey
import net.gutefrage.mandrill.messages.SendTemplate

/**
 *
 * == Messages API ==
 *
 * API for generating requests.
 *
 * @param apiKey
 */
class Messages(apiKey: MandrillApiKey) {

  /**
   *
   * @param templateName Mandrill template slug
   * @return request body for the send-template api endpoint
   */
  def sendTemplate(templateName: String): SendTemplate = SendTemplate(
    key = apiKey,
    template_name = SendTemplate.TemplateName(templateName)
  )

}
