package moe.feng.common.android.coroutine

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

inline fun <reified T> ui(noinline block: suspend CoroutineScope.() -> T): Deferred<T> =
        async(UI) {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }

inline suspend fun <reified T> await(noinline block: suspend CoroutineScope.() -> T): T =
        async(CommonPool) {
            try {
                block()
            } catch (e: Exception) {
                e.printStackTrace()
                throw e
            }
        }.await()