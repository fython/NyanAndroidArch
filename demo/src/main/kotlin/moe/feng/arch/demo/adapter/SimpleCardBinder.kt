package moe.feng.arch.demo.adapter

import moe.feng.common.arch.demo.R
import moe.feng.common.arch.demo.databinding.SampleItemIntegerBinding
import moe.feng.common.arch.list.ItemBinder
import moe.feng.common.kt.singleton

open class SimpleCardBinder : ItemBinder<SimpleCardBinder, SampleItemIntegerBinding>(R.layout.sample_item_card) {

    open val text = "Binder can be a card too!"

    companion object {

        val INSTANCE: SimpleCardBinder by singleton()

    }

}
