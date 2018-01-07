@file:JvmMultifileClass
package moe.feng.common.android

import android.preference.Preference
import android.preference.PreferenceFragment
import moe.feng.common.kt.StringUtil
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

fun <T : Preference> PreferenceFragment.bindPreference(
        key: String? = null,
        autoBindListener: Boolean = false
): PreferenceBinder<T> = PreferenceBinder(key, autoBindListener)

class PreferenceBinder<out T : Preference> internal constructor(
        private val key: String? = null,
        private val autoBindListener: Boolean = false
) : ReadOnlyProperty<PreferenceFragment, T?> {

    override fun getValue(thisRef: PreferenceFragment, property: KProperty<*>): T? {
        val pref = thisRef.findPreference(key ?: StringUtil.toCamelCase(property.name)) as? T
        if (autoBindListener) {
            if (thisRef is Preference.OnPreferenceClickListener) {
                pref?.onPreferenceClickListener = thisRef
            }
            if (thisRef is Preference.OnPreferenceChangeListener) {
                pref?.onPreferenceChangeListener = thisRef
            }
        }
        return pref
    }

}