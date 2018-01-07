package moe.feng.common.arch.support.gson

import com.google.gson.Gson
import java.io.Reader
import java.lang.reflect.Type

object GsonUtils {

    private var gson = Gson()

    fun setGsonInstance(gson: Gson) {
        GsonUtils.gson = gson
    }

    fun getGsonInstance(): Gson = gson

    inline fun <reified T> fromJson(json: String): T = fromJson(json, T::class.java)

    fun <T> fromJson(json: String, clazz: Class<T>): T = gson.fromJson(json, clazz)

    fun <T> fromJson(reader: Reader, clazz: Class<T>): T = gson.fromJson(reader, clazz)

    fun <T> fromJson(json: String, type: Type): T = gson.fromJson(json, type)

    fun toJson(obj: Any): String = gson.toJson(obj)

}

inline fun Any.toJsonString(): String {
    return GsonUtils.toJson(this)
}

inline fun <reified T> String.toObject(): T {
    return GsonUtils.fromJson(this)
}