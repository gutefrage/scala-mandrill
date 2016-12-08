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
