package moe.feng.arch.demo

import android.databinding.ObservableField
import moe.feng.arch.demo.adapter.AnotherSimpleCardBinder
import moe.feng.arch.demo.adapter.SimpleCardBinder

class MainViewModel {

    val number: ObservableField<Int> = ObservableField(0)
    val listItems: ObservableField<MutableList<Any>> = ObservableField(mutableListOf())

    fun incNumber() {
        number.set(number.get() + 1)
    }

    fun decNumber() {
        number.set(number.get() - 1)
    }

    internal fun mockData() {
        listItems.set(
                mutableListOf(
                        SimpleCardBinder.INSTANCE,
                        AnotherSimpleCardBinder.INSTANCE,
                        "Test",
                        123456,
                        "Hello,",
                        "world!",
                        AnotherSimpleCardBinder.INSTANCE,
                        "/eat@RikkaW",
                        *(1..20).asIterable().map { it * it }.toTypedArray(),
                        "!"
                )
        )
    }

    fun getItemSpanSize(position: Int): Int = if (position > 1) 1 else 2

}