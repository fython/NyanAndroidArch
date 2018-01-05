@file:JvmMultifileClass
package moe.feng.common.android

import android.content.Context
import android.content.SharedPreferences
import moe.feng.common.kt.classOf
import moe.feng.common.kt.getInstance
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

abstract class SharedPreferencesProvider
@JvmOverloads constructor(
        context: Context,
        prefName: String = "default",
        mode: Int = Context.MODE_PRIVATE
) : ISharedPreferencesProvider {

    override val sharedPref: SharedPreferences = context.getSharedPreferences(prefName, mode)

}

@JvmName("SharedPreferencesUtils")
fun <T: SharedPreferencesProvider> Context.initSharedPreferencesProvider(clazz: Class<T>): T {
    return getInstance(clazz) {
        clazz.getDeclaredConstructor(Context::class.java)
                .apply { isAccessible = true }
                .newInstance(this) as T
    }
}

@JvmName("SharedPreferencesUtils")
inline fun <reified T: SharedPreferencesProvider> Context.initSharedPreferencesProvider(): T {
    return initSharedPreferencesProvider(T::class.java)
}

@JvmName("SharedPreferencesUtils")
inline fun <reified T: SharedPreferencesProvider> getSharedPreferencesProvider(): T {
    return getInstance()
}

@JvmName("SharedPreferencesUtils")
operator fun SharedPreferences.get(key: String): SharedPreferencesGetter?
        = if (contains(key)) SharedPreferencesGetter(this, key) else null

class SharedPreferencesGetter internal constructor(internal val sp: SharedPreferences, val key: String) {
    fun asString(defValue: String? = null): String? = sp.getString(key, defValue)
    fun asInt(defValue: Int = 0): Int = sp.getInt(key, defValue)
    fun asBoolean(defValue: Boolean = false): Boolean = sp.getBoolean(key, defValue)
    fun asLong(defValue: Long = 0): Long = sp.getLong(key, defValue)
    fun asFloat(defValue: Float = 0F): Float = sp.getFloat(key, defValue)
    fun asStringSet(defValue: Set<String> = emptySet()): Set<String> = sp.getStringSet(key, defValue)
}

@JvmName("SharedPreferencesUtils")
fun SharedPreferencesGetter?.asString(defValue: String? = null): String?
        = this?.sp?.getString(key, defValue) ?: defValue
@JvmName("SharedPreferencesUtils")
fun SharedPreferencesGetter?.asInt(defValue: Int = 0): Int
        = this?.sp?.getInt(key, defValue) ?: defValue
@JvmName("SharedPreferencesUtils")
fun SharedPreferencesGetter?.asBoolean(defValue: Boolean = false): Boolean
        = this?.sp?.getBoolean(key, defValue) ?: defValue
@JvmName("SharedPreferencesUtils")
fun SharedPreferencesGetter?.asLong(defValue: Long = 0): Long
        = this?.sp?.getLong(key, defValue) ?: defValue
@JvmName("SharedPreferencesUtils")
fun SharedPreferencesGetter?.asFloat(defValue: Float = 0F): Float
        = this?.sp?.getFloat(key, defValue) ?: defValue
@JvmName("SharedPreferencesUtils")
fun SharedPreferencesGetter?.asStringSet(defValue: Set<String> = emptySet()): Set<String>
        = this?.sp?.getStringSet(key, defValue) ?: defValue

@JvmName("SharedPreferencesUtils")
operator fun SharedPreferences.set(key: String, value: Any?) {
    when (value) {
        is String -> edit().putString(key, value).apply()
        is Int -> edit().putInt(key, value).apply()
        is Boolean -> edit().putBoolean(key, value).apply()
        is Long -> edit().putLong(key, value).apply()
        is Float -> edit().putFloat(key, value).apply()
        else -> {
            if (value?.javaClass == classOf<Set<String>>()) {
                edit().putStringSet(key, value as Set<String>).apply()
            }
        }
    }
}

interface ISharedPreferencesProvider {

    val sharedPref: SharedPreferences

    fun stringValue(key: String? = null, defValue: String? = null) = StringSharedPreferenceGetter(key, defValue)
    fun intValue(key: String? = null, defValue: Int = 0) = IntSharedPreferenceGetter(key, defValue)
    fun booleanValue(key: String? = null, defValue: Boolean = false) = BooleanSharedPreferenceGetter(key, defValue)
    fun longValue(key: String? = null, defValue: Long = 0) = LongSharedPreferenceGetter(key, defValue)
    fun floatValue(key: String? = null, defValue: Float = 0F) = FloatSharedPreferenceGetter(key, defValue)

}

class StringSharedPreferenceGetter internal constructor(private val key: String? = null,
                                                        private val defValue: String? = null)
    : ReadWriteProperty<ISharedPreferencesProvider, String?> {

    override fun getValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>): String?
            = thisRef.sharedPref[key ?: property.name].asString(defValue)

    override fun setValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>, value: String?) {
        thisRef.sharedPref[key ?: property.name] = value
    }

}

class IntSharedPreferenceGetter internal constructor(private val key: String? = null,
                                                     private val defValue: Int = 0)
    : ReadWriteProperty<ISharedPreferencesProvider, Int> {

    override fun getValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>): Int
            = thisRef.sharedPref[key ?: property.name].asInt(defValue)

    override fun setValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>, value: Int) {
        thisRef.sharedPref[key ?: property.name] = value
    }

}

class BooleanSharedPreferenceGetter internal constructor(private val key: String? = null,
                                                         private val defValue: Boolean = false)
    : ReadWriteProperty<ISharedPreferencesProvider, Boolean> {

    override fun getValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>): Boolean
            = thisRef.sharedPref[key ?: property.name].asBoolean(defValue)

    override fun setValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>, value: Boolean) {
        thisRef.sharedPref[key ?: property.name] = value
    }

}

class LongSharedPreferenceGetter internal constructor(private val key: String? = null,
                                                      private val defValue: Long = 0)
    : ReadWriteProperty<ISharedPreferencesProvider, Long> {

    override fun getValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>): Long
            = thisRef.sharedPref[key ?: property.name].asLong(defValue)

    override fun setValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>, value: Long) {
        thisRef.sharedPref[key ?: property.name] = value
    }

}

class FloatSharedPreferenceGetter internal constructor(private val key: String? = null,
                                                       private val defValue: Float = 0F)
    : ReadWriteProperty<ISharedPreferencesProvider, Float> {

    override fun getValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>): Float
            = thisRef.sharedPref[key ?: property.name].asFloat(defValue)

    override fun setValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>, value: Float) {
        thisRef.sharedPref[key ?: property.name] = value
    }

}

class StringSetSharedPreferenceGetter internal constructor(private val key: String? = null,
                                                       private val defValue: Set<String> = emptySet())
    : ReadWriteProperty<ISharedPreferencesProvider, Set<String>> {

    override fun getValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>): Set<String>
            = thisRef.sharedPref[key ?: property.name].asStringSet(defValue)

    override fun setValue(thisRef: ISharedPreferencesProvider, property: KProperty<*>, value: Set<String>) {
        thisRef.sharedPref[key ?: property.name] = value
    }

}