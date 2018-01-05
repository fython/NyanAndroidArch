package moe.feng.arch.demo

import android.databinding.ObservableField
import android.net.Uri
import moe.feng.arch.demo.adapter.AnotherSimpleCardBinder
import moe.feng.arch.demo.adapter.SimpleCardBinder
import moe.feng.common.android.coroutine.await
import moe.feng.common.android.coroutine.ui

class MainViewModel {

    val number: ObservableField<Int> = ObservableField(0)
    val listItems: ObservableField<MutableList<Any>> = ObservableField(mutableListOf())

    fun incNumber() {
        number.set(number.get() + 1)
    }

    fun decNumber() {
        number.set(number.get() - 1)
    }

    internal fun mockData() = ui {
        listItems.set(await {
                mutableListOf(
                        SimpleCardBinder.INSTANCE,
                        AnotherSimpleCardBinder.INSTANCE,
                        "Test",
                        123456,
                        "Hello,",
                        "world!",
                        AnotherSimpleCardBinder.INSTANCE,
                        Uri.parse("http://wx1.sinaimg.cn/large/6f76b6dagy1fg5rg39y07j20r80hskbd.jpg"),
                        "/eat@RikkaW",
                        *(1..20).asIterable().map { it * it }.toTypedArray(),
                        "!"
                )
        })
    }

    fun getItemSpanSize(position: Int): Int = if (position > 1) 1 else 2

}