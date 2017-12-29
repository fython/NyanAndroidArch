package moe.feng.arch.demo.adapter

import android.util.Log
import moe.feng.common.arch.demo.R
import moe.feng.common.arch.demo.databinding.SampleItemStringBinding
import moe.feng.common.arch.list.BindingItemBinder
import moe.feng.common.arch.list.IBindingViewHolder
import moe.feng.common.kt.TAG


class StringBinder: BindingItemBinder<String, SampleItemStringBinding>(R.layout.sample_item_string) {

    override fun onViewHolderCreated(holder: IBindingViewHolder<String, SampleItemStringBinding>) {
        super.onViewHolderCreated(holder)
        Log.d(TAG, "onViewHolderCreated")
    }

}