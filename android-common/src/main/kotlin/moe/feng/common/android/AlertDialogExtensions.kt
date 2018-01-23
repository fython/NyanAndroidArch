package moe.feng.common.android

import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.View
import android.widget.AdapterView
import android.widget.Button

/**
 * Build AlertDialog in Activity
 *
 * @param process The process of building AlertDialog
 * @see android.app.AlertDialog
 */
fun Activity.buildAlertDialog(process: AlertDialog.Builder.() -> Unit) : AlertDialog {
    val builder = AlertDialog.Builder(this)
    builder.process()
    return builder.create()
}

var AlertDialog.Builder.title : String
    get() { throw NoSuchMethodException("Title getter is not supported")}
    set(value) { this.setTitle(value) }

var AlertDialog.Builder.titleRes : Int
    get() { throw NoSuchMethodException("Title res id getter is not supported")}
    set(value) { this.setTitle(value) }

var AlertDialog.Builder.message : String
    get() { throw NoSuchMethodException("Message getter is not supported")}
    set(value) { this.setMessage(value) }

var AlertDialog.Builder.messageRes : Int
    get() { throw NoSuchMethodException("Message res id getter is not supported")}
    set(value) { this.setMessage(value) }

var AlertDialog.Builder.isCancelable : Boolean
    get() { throw NoSuchMethodException("isCancelable getter is not supported")}
    set(value) { this.setCancelable(value) }

var AlertDialog.Builder.customTitle : View
    get() { throw NoSuchMethodException("Custom title getter is not supported")}
    set(value) { this.setCustomTitle(value) }

var AlertDialog.Builder.icon : Drawable
    get() { throw NoSuchMethodException("Icon getter is not supported")}
    set(value) { this.setIcon(value) }

var AlertDialog.Builder.iconRes : Int
    get() { throw NoSuchMethodException("Icon res id getter is not supported")}
    set(value) { this.setIcon(value) }

var AlertDialog.Builder.iconAttribute : Int
    get() { throw NoSuchMethodException("Icon attribute getter is not supported")}
    set(value) { this.setIconAttribute(value) }

var AlertDialog.Builder.onCancel : (DialogInterface) -> Unit
    get() { throw NoSuchMethodException("OnCancelListener getter is not supported")}
    set(value) { this.setOnCancelListener(value) }

var AlertDialog.Builder.onDismiss : (DialogInterface) -> Unit
    get() { throw NoSuchMethodException("OnDismissListener getter is not supported")}
    set(value) { this.setOnDismissListener(value) }

var AlertDialog.Builder.onKey : DialogInterface.OnKeyListener
    get() { throw NoSuchMethodException("OnKeyListener getter is not supported")}
    set(value) { this.setOnKeyListener(value) }

var AlertDialog.Builder.onItemSelected : AdapterView.OnItemSelectedListener
    get() { throw NoSuchMethodException("OnItemSelectedListener getter is not supported")}
    set(value) { this.setOnItemSelectedListener(value) }

var AlertDialog.Builder.view : View
    get() { throw NoSuchMethodException("View getter is not supported")}
    set(value) { this.setView(value) }

var AlertDialog.Builder.viewRes : Int
    get() { throw NoSuchMethodException("View res id getter is not supported")}
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    set(value) { this.setView(value) }

/**
 * Set ok button for AlertDialog
 *
 * @param onClick onClick callback
 */
fun AlertDialog.Builder.okButton(onClick: (DialogInterface, Int) -> Unit = {_, _ ->}) {
    setPositiveButton(android.R.string.ok, onClick)
}

/**
 * Set cancel button for AlertDialog
 *
 * @param onClick onClick callback
 */
fun AlertDialog.Builder.cancelButton(onClick: (DialogInterface, Int) -> Unit = {_, _ ->}) {
    setNegativeButton(android.R.string.cancel, onClick)
}

/**
 * Set positive button for AlertDialog
 *
 * @param textId Text resource id
 * @param onClick onClick callback
 */
fun AlertDialog.Builder.positiveButton(textId: Int, onClick: (DialogInterface, Int) -> Unit) {
    setPositiveButton(textId, onClick)
}

/**
 * Set positive button for AlertDialog
 *
 * @param text Text string
 * @param onClick onClick callback
 */
fun AlertDialog.Builder.positiveButton(text: String, onClick: (DialogInterface, Int) -> Unit) {
    setPositiveButton(text, onClick)
}

/**
 * Set negative button for AlertDialog
 *
 * @param textId Text resource id
 * @param onClick onClick callback
 */
fun AlertDialog.Builder.negativeButton(textId: Int, onClick: (DialogInterface, Int) -> Unit) {
    setNegativeButton(textId, onClick)
}

/**
 * Set negative button for AlertDialog
 *
 * @param text Text string
 * @param onClick onClick callback
 */
fun AlertDialog.Builder.negativeButton(text: String, onClick: (DialogInterface, Int) -> Unit) {
    setNegativeButton(text, onClick)
}

/**
 * Set neutral button for AlertDialog
 *
 * @param textId Text resource id
 * @param onClick onClick callback
 */
fun AlertDialog.Builder.neutralButton(textId: Int, onClick: (DialogInterface, Int) -> Unit) {
    setNeutralButton(textId, onClick)
}

/**
 * Set neutral button for AlertDialog
 *
 * @param text Text string
 * @param onClick onClick callback
 */
fun AlertDialog.Builder.neutralButton(text: String, onClick: (DialogInterface, Int) -> Unit) {
    setNeutralButton(text, onClick)
}

fun <T : Dialog> T.afterViewCreated(block: (T) -> Unit) : T {
    this.setOnShowListener { block(this) }
    return this
}

val AlertDialog.positiveButton : Button
    get() = this.getButton(DialogInterface.BUTTON_POSITIVE)

val AlertDialog.negativeButton : Button
    get() = this.getButton(DialogInterface.BUTTON_NEGATIVE)

val AlertDialog.neutralButton : Button
    get() = this.getButton(DialogInterface.BUTTON_NEUTRAL)