package moe.feng.common.kt

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class SingletonExtensionsTest {

    val singletonInt by singleton {
        println("Initialized.")
        123
    }

    @Before fun initialize() {
        println("value=$singletonInt")
    }

    @Test fun test_getSingleton() {
        println("value=$singletonInt")
        releaseInstance<Int>()
        println("value=$singletonInt")
    }

}