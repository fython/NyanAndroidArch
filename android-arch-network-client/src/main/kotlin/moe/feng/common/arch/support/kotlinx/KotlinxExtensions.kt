package moe.feng.common.arch.support.kotlinx

import kotlinx.serialization.*
import kotlinx.serialization.json.JSON

object KotlinxUtils {

    private var jsonInstance = JSON.plain

    fun setJSONInstance(instance: JSON) {
        jsonInstance = instance
    }

    fun getJSONInstance() = jsonInstance

    @JvmOverloads
    inline fun <reified T : Any> toJson(obj: T, saver: KSerialSaver<T>? = null): String {
        return if (saver == null)
            getJSONInstance().stringify(obj) else getJSONInstance().stringify(saver, obj)
    }

    @JvmOverloads
    inline fun <reified T : Any> fromJson(string: String, loader: KSerialLoader<T>? = null): T {
        return if (loader == null)
            getJSONInstance().parse(string) else getJSONInstance().parse(loader, string)
    }

}

@JvmOverloads
inline fun <reified T : Any> T.toJsonString(saver: KSerialSaver<T>? = null): String {
    return KotlinxUtils.toJson(this, saver)
}

@JvmOverloads
inline fun <reified T : Any> String.toObject(loader: KSerialLoader<T>? = null): T {
    return KotlinxUtils.fromJson(this, loader)
}

inline fun <reified T : Any> String.toList(): List<T> {
    return KotlinxUtils.fromJson(this, T::class.serializer().list)
}

inline fun <reified T : Any> String.toSet(): Set<T> {
    return KotlinxUtils.fromJson(this, T::class.serializer().set)
}

inline fun <reified K : Any, reified V : Any> String.toMap(): Map<K, V> {
    return KotlinxUtils.fromJson(this, (K::class.serializer() to V::class.serializer()).map)
}