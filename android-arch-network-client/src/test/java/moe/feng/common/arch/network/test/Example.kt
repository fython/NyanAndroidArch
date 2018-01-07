package moe.feng.common.arch.network.test

import kotlinx.coroutines.experimental.launch
import moe.feng.common.arch.support.okhttp.ApiMethod
import moe.feng.common.arch.support.okhttp.RestfulApiCollection
import moe.feng.common.arch.support.okhttp.requestListByKotlinx

object ExampleApi : RestfulApiCollection() {

    val homepageList by methodGET<GetHomepageListMethod>()

    class GetHomepageListMethod : BaseApiMethod() {

        override val path: String = "api"

        init {
            addDefaultQueryParam("sort", 0)
            addHeader("Default-Header", "Hello")
        }

    }

    abstract class BaseApiMethod
    internal constructor(override val host: String = "https://example.com") : ApiMethod()

}

object ExampleApiTest {

    @JvmStatic
    fun test_homepageList() {
        launch {
            val resultList = ExampleApi.homepageList()
                    .queryParams("id" to "my_id_123456")
                    .newCall()
                    .requestListByKotlinx<String>()
        }
    }

}