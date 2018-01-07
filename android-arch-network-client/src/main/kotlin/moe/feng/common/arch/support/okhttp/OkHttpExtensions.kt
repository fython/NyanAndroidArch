package moe.feng.common.arch.support.okhttp

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import kotlinx.serialization.*
import moe.feng.common.arch.support.gson.GsonUtils
import moe.feng.common.arch.support.kotlinx.KotlinxUtils
import okhttp3.*

object OkHttpUtils {

    private var client = OkHttpClient()

    fun setClient(client: OkHttpClient) {
        this.client = client
    }

    fun getClient(): OkHttpClient {
        return this.client
    }

}

inline fun Request.Builder.call(): Call = this.build().call()

inline fun Request.call(): Call = OkHttpUtils.getClient().newCall(this)

inline suspend fun Call.requestBody(): Pair<Int, ResponseBody?> =
        execute().let { it.code() to it.body() }

fun Call.requestBodyAsync(): Deferred<Pair<Int, ResponseBody?>> = async(CommonPool) { requestBody() }

suspend fun Call.requestString(): String? {
    val (responseCode, body) = requestBody()
    if (responseCode == 200) {
        return try {
            body?.string()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
    return null
}

suspend inline fun <reified T : Any> Call.requestObjectByGson(): T? {
    return requestString()?.let(GsonUtils::fromJson)
}

suspend inline fun <reified T : Any> Call.requestObjectByKotlinx(
        loader: KSerialLoader<T>? = null): T? {
    return requestString()?.let { KotlinxUtils.fromJson(it, loader) }
}

suspend inline fun <reified T : Any> Call.requestListByKotlinx(): List<T>? {
    return requestString()?.let { KotlinxUtils.fromJson(it, T::class.serializer().list) }
}

suspend inline fun <reified T : Any> Call.requestSetByKotlinx(): Set<T>? {
    return requestString()?.let { KotlinxUtils.fromJson(it, T::class.serializer().set) }
}

suspend inline fun <reified K : Any, reified V : Any> Call.requestMapByKotlinx(): Map<K, V>? {
    return requestString()?.let {
        KotlinxUtils.fromJson(it, (K::class.serializer() to V::class.serializer()).map)
    }
}