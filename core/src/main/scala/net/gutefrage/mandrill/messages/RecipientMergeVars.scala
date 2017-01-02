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

package net.gutefrage.mandrill.messages

/**
 * == Recipient MergeVars ==
 *
 * Represents the `merge_vars` array in the API definition.
 *
 * == API doc ==
 *
 * per-recipient merge variables, which override global merge variables with the same name.
 *
 * @param recipient The recipient associated with the given `merge_vars`. See [[Recipient]]
 * @param merge_vars The specific merge_vars for the given `recipient`. See [[MergeVar]]
 * @see [[SendTemplate]]
 */
final case class RecipientMergeVars(recipient: Recipient, merge_vars: Seq[MergeVar])
