package moe.feng.common.android

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.os.Parcelable
import android.util.Size
import android.util.SizeF
import java.io.Serializable

fun buildBundle(initializer: Bundle.() -> Unit): Bundle = Bundle().apply(initializer)

operator fun Bundle.set(key: String, value: Any?) {
    when (value) {
        is Boolean -> putBoolean(key, value)
        is Byte -> putByte(key, value)
        is ByteArray -> putByteArray(key, value)
        is IBinder -> putBinder(key, value)
        is Char -> putChar(key, value)
        is CharArray -> putCharArray(key, value)
        is CharSequence -> putCharSequence(key, value)
        is Double -> putDouble(key, value)
        is DoubleArray -> putDoubleArray(key, value)
        is Float -> putFloat(key, value)
        is FloatArray -> putFloatArray(key, value)
        is Int -> putInt(key, value)
        is IntArray -> putIntArray(key, value)
        is Long -> putLong(key, value)
        is Short -> putShort(key, value)
        is ShortArray -> putShortArray(key, value)
        is Parcelable -> putParcelable(key, value)
        is Serializable -> putSerializable(key, value)
        is String -> putString(key, value)
        is Array<*> -> when (value.getOrNull(0)) {
            is CharSequence -> putCharSequenceArray(key, value as Array<out CharSequence>)
            is String -> putStringArray(key, value as Array<out String>)
            is Parcelable -> putParcelableArray(key, value as Array<out Parcelable>)
        }
        is ArrayList<*> -> when (value.getOrNull(0)) {
            is Int -> putIntegerArrayList(key, value as java.util.ArrayList<Int>)
            is Integer -> putIntegerArrayList(key, value as java.util.ArrayList<Int>)
            is CharSequence -> putCharSequenceArrayList(key, value as java.util.ArrayList<CharSequence>)
            is String -> putStringArrayList(key, value as java.util.ArrayList<String>)
            is Parcelable -> putParcelableArrayList(key, value as java.util.ArrayList<Parcelable>)
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> when (value) {
            is Size -> putSize(key, value)
            is SizeF -> putSizeF(key, value)
        }
        null -> {} // Ignore null pointer
        else -> throw UnsupportedOperationException("Unsupported type ${value.javaClass.name} in params")
    }
}