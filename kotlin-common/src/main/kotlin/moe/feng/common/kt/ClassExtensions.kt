@file:JvmName("ClassUtils")

package moe.feng.common.kt

/**
 * Get the tag (simple name) of class.
 */
inline val Any.TAG: String
    /**
     * @return The simple name of class
     */
    get() = this.javaClass.simpleName


/**
 * Get java class by generic type
 *
 * @return Java Class
 */
inline fun <reified T> classOf(): Class<T> = T::class.java


/**
 * New instance of class without arguments
 *
 * @param clazz Class
 * @return The new instance of class
 */
fun <T: Any> newInstanceWithoutArgs(clazz: Class<T>): T =
        clazz.getConstructor().apply { isAccessible = true }.newInstance()

/**
 * New instance of class without arguments
 *
 * @return The new instance of class
 */
inline fun <reified T: Any> newInstanceWithoutArgs(): T = newInstanceWithoutArgs(classOf())