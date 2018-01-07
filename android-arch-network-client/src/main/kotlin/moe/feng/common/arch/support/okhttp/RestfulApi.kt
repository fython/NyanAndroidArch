package moe.feng.common.arch.support.okhttp

import android.util.Log
import moe.feng.common.kt.ObjectsUtil
import moe.feng.common.kt.Singleton
import okhttp3.*
import java.io.File
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

private const val TAG = "RestfulApiUtils"

open class RestfulApiCollection {

    inline fun <reified T : ApiMethod> method() = methodGET(T::class.java)

    inline fun <reified T : ApiMethod> methodGET() = methodGET(T::class.java)

    inline fun <reified T : ApiMethod> methodPOST() = methodPOST(T::class.java)

    inline fun <reified T : ApiMethod> methodPUT() = methodPUT(T::class.java)

    inline fun <reified T : ApiMethod> methodDELETE() = methodDELETE(T::class.java)

    inline fun <reified T : ApiMethod> methodPATCH() = methodPATCH(T::class.java)

}

fun <T : ApiMethod> RestfulApiCollection.methodGET(clazz: Class<T>) =
        ApiMethodProperty(clazz, "GET")

fun <T : ApiMethod> RestfulApiCollection.methodPOST(clazz: Class<T>) =
        ApiMethodProperty(clazz, "POST")

fun <T : ApiMethod> RestfulApiCollection.methodPUT(clazz: Class<T>) =
        ApiMethodProperty(clazz, "PUT")

fun <T : ApiMethod> RestfulApiCollection.methodDELETE(clazz: Class<T>) =
        ApiMethodProperty(clazz, "DELETE")

fun <T : ApiMethod> RestfulApiCollection.methodPATCH(clazz: Class<T>) =
        ApiMethodProperty(clazz, "PATCH")

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
    private val headerAddMap = mutableMapOf<String, String>()
    private val headerSetMap = mutableMapOf<String, String>()
    private val headerRemoveList = mutableListOf<String>()
    var requestBodyCreator: (() -> RequestBody)? = null
    var cacheControlCreator: (() -> CacheControl)? = null

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

    protected fun addHeader(key: String, value: String) {
        headerAddMap += key to value
    }

    protected fun setHeader(key: String, value: String) {
        headerSetMap += key to value
    }

    protected fun removeHeader(key: String) {
        headerAddMap.remove(key)
        headerSetMap.remove(key)
        headerRemoveList += key
    }

    protected fun requestBodyCreateByJson(string: String) {
        requestBodyCreator = {
            RequestBody.create(MediaType.parse("application/json"), string)
        }
    }

    protected fun requestBodyCreateByPostForm(vararg formItem: Pair<String, String>) {
        requestBodyCreateByPostForm(mapOf(*formItem))
    }

    protected fun requestBodyCreateByPostForm(form: Map<String, String>) {
        requestBodyCreator = {
            RequestBody.create(
                MediaType.parse("application/x-www-form-urlencoded"),
                form.map { it.key + "=" + it.value }.reduce { acc, s -> "$acc&$s" }
            )
        }
    }

    class CallBuilder internal constructor(private val apiMethod: ApiMethod) {

        private val pathParams = LinkedHashMap<String, Any>(
                apiMethod.pathParamsMap.filterValues(ObjectsUtil::nonNull)
        )
        private val queryParams = LinkedHashMap<String, Any>(
                apiMethod.queryParamsMap.filterValues(ObjectsUtil::nonNull)
        )
        private val headerAddMap = LinkedHashMap<String, String>(apiMethod.headerAddMap)
        private val headerSetMap = LinkedHashMap<String, String>(apiMethod.headerSetMap)
        private val headerRemoveList = ArrayList<String>(apiMethod.headerRemoveList)
        private var requestBody = apiMethod.requestBodyCreator?.invoke()
        private var cacheControl = apiMethod.cacheControlCreator?.invoke()

        fun pathParams(vararg pathParams: Pair<String, Any>): CallBuilder {
            this.pathParams += pathParams
            return this
        }

        fun queryParams(vararg queryParams: Pair<String, Any>): CallBuilder {
            this.queryParams += queryParams
            return this
        }

        fun addHeader(key: String, value: String): CallBuilder {
            headerAddMap += key to value
            return this
        }

        fun setHeader(key: String, value: String): CallBuilder {
            headerSetMap += key to value
            return this
        }

        fun removeHeader(key: String): CallBuilder {
            headerAddMap.remove(key)
            headerRemoveList += key
            return this
        }

        fun setRequestBody(requestBody: RequestBody): CallBuilder {
            this.requestBody = requestBody
            return this
        }

        fun setCacheControl(cacheControl: CacheControl): CallBuilder {
            this.cacheControl = cacheControl
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
                    .apply {
                        url(getRequestUrl())
                        when (apiMethod.method.toUpperCase()) {
                            "GET" -> {
                                get()
                                if (requestBody != null) {
                                    Log.w(TAG, "GET method should not contains a request body. It has been ignored.")
                                }
                            }
                            "POST" -> {
                                if (requestBody == null) {
                                    throw IllegalArgumentException("POST method should contains a request body.")
                                } else {
                                    post(requestBody)
                                }
                            }
                            "DELETE" -> {
                                delete(requestBody)
                            }
                            "PUT" -> {
                                if (requestBody == null) {
                                    throw IllegalArgumentException("PUT method should contains a request body.")
                                }
                                put(requestBody)
                            }
                            "PATCH" -> {
                                if (requestBody == null) {
                                    throw IllegalArgumentException("PATCH method should contains a request body.")
                                }
                                patch(requestBody)
                            }
                        }
                        headerSetMap.forEach { (key, value) -> header(key, value) }
                        headerAddMap.forEach { (key, value) -> addHeader(key, value) }
                        headerRemoveList.map(::removeHeader)
                        if (cacheControl != null) {
                            cacheControl(cacheControl)
                        }
                    }
                    .call()
        }

    }

}