package moe.feng.arch.demo.adapter

import android.util.Log
import moe.feng.common.arch.demo.R
import moe.feng.common.arch.demo.databinding.SampleItemCardBinding
import moe.feng.common.arch.list.BindingItemBinder
import moe.feng.common.arch.list.IBindingViewHolder
import moe.feng.common.kt.TAG
import moe.feng.common.kt.singleton

open class SimpleCardBinder : BindingItemBinder<SimpleCardBinder, SampleItemCardBinding>(R.layout.sample_item_card) {

    open val text = "Binder can be a card too!"

    override fun onViewHolderCreated(holder: IBindingViewHolder<SimpleCardBinder, SampleItemCardBinding>) {
        super.onViewHolderCreated(holder)
        Log.d(TAG, "onViewHolderCreated")
    }

    override fun getItemSpanSize(data: SimpleCardBinder, position: Int): Int {
        return 2
    }

    companion object {

        val INSTANCE: SimpleCardBinder by singleton()

    }

}
