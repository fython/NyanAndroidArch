package moe.feng.common.arch.support.okhttp

import moe.feng.common.kt.ObjectsUtil
import moe.feng.common.kt.Singleton
import okhttp3.Call
import okhttp3.Request
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

open class RestfulApiCollection {

    inline fun <reified T : ApiMethod> method() = methodGET(T::class.java)

    inline fun <reified T : ApiMethod> methodGET() = methodGET(T::class.java)

    inline fun <reified T : ApiMethod> methodPOST() = methodPOST(T::class.java)

}

fun <T : ApiMethod> RestfulApiCollection.methodGET(clazz: Class<T>) =
        ApiMethodProperty(clazz, "GET")

fun <T : ApiMethod> RestfulApiCollection.methodPOST(clazz: Class<T>) =
        ApiMethodProperty(clazz, "POST")

class ApiMethodProperty<out T : ApiMethod> internal constructor(
        clazz: Class<T>,
        private val method: String = "GET"
) : ReadOnlyProperty<RestfulApiCollection, T> {

    private val apiMethod by Singleton(clazz)

    override fun getValue(thisRef: RestfulApiCollection, property: KProperty<*>): T {
        return apiMethod.apply { method = this@ApiMethodProperty.method }
    }

}

abstract class ApiMethod {

    /**
     * Http method name
     */
    var method: String = "GET"

    /**
     * Api host
     * <p>
     * For example: https://example.com
     */
    abstract val host: String

    /**
     * Api path
     * <p>
     * For example:
     * path/a/b
     * path/{id}
     */
    abstract val path: String

    private val queryParamsMap = mutableMapOf<String, Any?>()
    private val pathParamsMap = mutableMapOf<String, Any?>()

    operator fun invoke(): CallBuilder = CallBuilder(this)

    protected fun addDefaultQueryParam(key: String, defValue: Any?) {
        queryParamsMap += key to defValue
    }

    protected fun Pair<String, Any?>.asDefaultQueryParam() {
        addDefaultQueryParam(this.first, this.second)
    }

    protected fun addDefaultPathParam(key: String, defValue: Any?) {
        pathParamsMap += key to defValue
    }

    protected fun Pair<String, Any?>.asDefaultPathParam() {
        addDefaultPathParam(this.first, this.second)
    }

    class CallBuilder internal constructor(private val apiMethod: ApiMethod) {

        private val pathParams = LinkedHashMap<String, Any>(
                apiMethod.pathParamsMap.filterValues(ObjectsUtil::nonNull)
        )
        private val queryParams = LinkedHashMap<String, Any>(
                apiMethod.queryParamsMap.filterValues(ObjectsUtil::nonNull)
        )

        fun pathParams(vararg pathParams: Pair<String, Any>): CallBuilder {
            this.pathParams += pathParams
            return this
        }

        fun queryParams(vararg queryParams: Pair<String, Any>): CallBuilder {
            this.queryParams += queryParams
            return this
        }

        fun getRequestUrl(): String = buildString {
            append(apiMethod.host)
            append("/")
            append(apiMethod.path.apply {
                apiMethod.pathParamsMap.forEach { (key, defValue) ->
                    replace("{$key}", (pathParams[key] ?: defValue).toString())
                }
            })
            if (queryParams.isNotEmpty()) {
                append("?")
                append(queryParams
                        .map { param -> "${param.key}=${param.value}" }
                        .reduce { total, next -> "$total&$next" })
            }
        }

        fun newCall(): Call {
            return Request.Builder()
                    .url(getRequestUrl())
                    .call()
        }

    }

}