@file:JvmName("SingletonUtils")

package moe.feng.common.kt

import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private val instances = mutableMapOf<Class<*>, Any>()

/**
 * Get if the instance of specific class exists
 *
 * @param clazz Specific class
 * @return Whether the instance of specific class exists
 */
fun hasInstance(clazz: Class<*>): Boolean = clazz in instances.keys

/**
 * Get if the instance of specific class exists
 *
 * @return Whether the instance of specific class exists
 */
inline fun <reified T> hasInstance(): Boolean = hasInstance(classOf<T>())

/**
 * Add instance to map.
 * <p>
 * Do not call this function outside this package.
 *
 * @param clazz Specific class
 * @param instance The instance
 */
fun <T: Any> addInstance(clazz: Class<T>, instance: T) {
    instances[clazz] = instance
}

/**
 * Add instance to map.
 * <p>
 * Do not call this function outside this package.
 *
 * @param instance The instance
 */
inline fun <reified T: Any> addInstance(instance: T) {
    addInstance(classOf(), instance)
}

/**
 * Get instance from map by class
 * <p>
 * Do not call this function outside this package.
 *
 * @param clazz Specific class
 * @return The instance of specific class
 */
fun <T: Any> getInstanceFromMap(clazz: Class<T>): T = instances[clazz] as T

/**
 * Get instance from map by class
 * <p>
 * Do not call this function outside this package.
 *
 * @return The instance of specific class
 */
inline fun <reified T: Any> getInstanceFromMap(): T = getInstanceFromMap(classOf())


/**
 * Get singleton instance by class
 *
 * @param clazz Class
 * @param defConstructor Default constructor
 * @return The instance of specific class
 */
@JvmOverloads
fun <T: Any> getInstance(
        clazz: Class<T>,
        defConstructor: () -> T = { newInstanceWithoutArgs(clazz) }
): T {
    if (!hasInstance(clazz)) {
        addInstance(clazz, defConstructor())
    }
    return getInstanceFromMap(clazz)
}

/**
 * Get singleton instance by class
 *
 * @param defConstructor Default constructor
 * @return The instance of specific class
 */
@JvmOverloads
inline fun <reified T: Any> getInstance(defConstructor: () -> T = ::newInstanceWithoutArgs): T {
    if (!hasInstance<T>()) {
        addInstance(defConstructor())
    }
    return getInstanceFromMap()
}


/**
 * Release the instance.
 *
 * @param clazz The class of instance you want to release
 */
fun releaseInstance(clazz: Class<*>) {
    if (instances.containsKey(clazz)) {
        instances.remove(clazz)
    }
}

/**
 * Release the instance by class.
 */
inline fun <reified T: Any> releaseInstance() = releaseInstance(classOf<T>())


/**
 * Singleton property
 *
 * @see ReadOnlyProperty
 */
class Singleton<out T : Any> constructor(
        private val clazz: Class<T>,
        private val defConstructor: () -> T = { newInstanceWithoutArgs(clazz) }
) : ReadOnlyProperty<Any, T> {

    override fun getValue(thisRef: Any, property: KProperty<*>): T {
        return getInstance(clazz, defConstructor)
    }

}

/**
 * Get singleton property without class argument
 *
 * @param defConstructor Default constructor
 */
inline fun <reified T : Any> singleton(noinline defConstructor: () -> T = ::newInstanceWithoutArgs)
        : Singleton<T> = Singleton(classOf(), defConstructor)