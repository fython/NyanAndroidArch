package moe.feng.common.arch

import android.support.annotation.IdRes
import android.view.View
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/*
 * PropertiesCreators
 */

class LazyBindViewCreator<out T: View> internal constructor(private @IdRes val id: Int) : ReadOnlyProperty<BaseActivity, T> {

    private @Volatile var view: T? = null

    override fun getValue(thisRef: BaseActivity, property: KProperty<*>): T {
        if (view == null) {
            view = thisRef.findViewById(id)
        }
        if (view != null) {
            return view!!
        } else {
            throw NullPointerException("Could not find id=$id in ${thisRef::class.java.simpleName}")
        }
    }

}

class LazyBindNullableViewCreator<out T: View> internal constructor(private @IdRes val id: Int) : ReadOnlyProperty<BaseActivity, T?> {

    private @Volatile var view: T? = null

    override fun getValue(thisRef: BaseActivity, property: KProperty<*>): T? {
        if (view == null) {
            view = thisRef.findViewById(id)
        }
        return view
    }

}