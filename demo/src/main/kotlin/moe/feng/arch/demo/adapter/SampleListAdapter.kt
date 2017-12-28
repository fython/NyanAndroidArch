package moe.feng.arch.demo.adapter

import android.util.Log
import moe.feng.common.arch.demo.BR
import moe.feng.common.arch.demo.R
import moe.feng.common.arch.demo.databinding.SampleItemIntegerBinding
import moe.feng.common.arch.demo.databinding.SampleItemStringBinding
import moe.feng.common.arch.list.BindingRecyclerViewAdapter
import moe.feng.common.arch.list.ItemBinder
import moe.feng.common.kt.TAG

class SampleListAdapter: BindingRecyclerViewAdapter(BR.item) {

    init {
        bind(StringBinder())
        bind<Int, SampleItemIntegerBinding>(R.layout.sample_item_integer)
        bindSelf(SimpleCardBinder.INSTANCE)
        bindSelf(AnotherSimpleCardBinder.INSTANCE)
    }

    class StringBinder: ItemBinder<String, SampleItemStringBinding>(R.layout.sample_item_string) {

        override fun onViewHolderCreated(holder: BindingHolder<String, SampleItemStringBinding>) {
            super.onViewHolderCreated(holder)
            Log.d(TAG, "onViewHolderCreated")
        }

    }

}
