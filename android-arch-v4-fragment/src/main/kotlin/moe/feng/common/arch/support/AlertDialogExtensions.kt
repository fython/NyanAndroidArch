package moe.feng.common.arch.support

import android.app.AlertDialog
import android.support.v4.app.Fragment
import moe.feng.common.android.buildAlertDialog

/**
 * Build AlertDialog in Fragment
 *
 * @param process The process of building AlertDialog
 * @see android.app.AlertDialog
 */
fun Fragment.buildAlertDialog(process: AlertDialog.Builder.() -> Unit) = activity?.buildAlertDialog(process)