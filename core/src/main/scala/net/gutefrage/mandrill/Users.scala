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
