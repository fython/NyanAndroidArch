package moe.feng.common.arch.support

import android.annotation.TargetApi
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 注意：
 * 这里不能用方法引用代替，Fragment 的 resources 可能是空值
 */
fun Fragment.dimenRes(id: Int, loadOnlyOnce: Boolean = true): ResourcesProperty<Float>
        = ResourcesProperty(id, { resources.getDimension(it) }, loadOnlyOnce)
fun Fragment.stringRes(id: Int, loadOnlyOnce: Boolean = true): ResourcesProperty<String>
        = ResourcesProperty(id, { resources.getString(it) }, loadOnlyOnce)
fun Fragment.integerRes(id: Int, loadOnlyOnce: Boolean = true): ResourcesProperty<Int>
        = ResourcesProperty(id, { resources.getInteger(it) }, loadOnlyOnce)
fun Fragment.boolRes(id: Int, loadOnlyOnce: Boolean = true): ResourcesProperty<Boolean>
        = ResourcesProperty(id, { resources.getBoolean(it) }, loadOnlyOnce)
fun Fragment.drawableRes(id: Int, loadOnlyOnce: Boolean = true): ResourcesProperty<Drawable>
        = ResourcesProperty(id, { resources.getDrawable(it) }, loadOnlyOnce)
fun Fragment.colorRes(id: Int, loadOnlyOnce: Boolean = true): ResourcesProperty<Int>
        = ResourcesProperty(id, { resources.getColor(it) }, loadOnlyOnce)
fun Fragment.stringArrayRes(id: Int, loadOnlyOnce: Boolean = true): ResourcesProperty<Array<String>>
        = ResourcesProperty(id, { resources.getStringArray(it) }, loadOnlyOnce)
fun Fragment.intArrayRes(id: Int, loadOnlyOnce: Boolean = true): ResourcesProperty<IntArray>
        = ResourcesProperty(id, { resources.getIntArray(it) }, loadOnlyOnce)
@TargetApi(26) fun Fragment.fontRes(id: Int, loadOnlyOnce: Boolean = true): ResourcesProperty<Typeface>
        = ResourcesProperty(id, { resources.getFont(it) }, loadOnlyOnce)

class ResourcesProperty<out T> (
        private val resourceId: Int,
        private val func: (resourceId: Int) -> T,
        private val loadOnlyOnce: Boolean = true): ReadOnlyProperty<Any, T> {

    private var _value: T? = null

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        if (_value == null || !loadOnlyOnce) _value = func(resourceId)
        return _value!!
    }

}