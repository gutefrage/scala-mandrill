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
