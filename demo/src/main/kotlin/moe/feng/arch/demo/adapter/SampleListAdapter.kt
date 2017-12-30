package moe.feng.arch.demo.adapter

import moe.feng.common.arch.demo.BR
import moe.feng.common.arch.demo.R
import moe.feng.common.arch.demo.databinding.SampleItemIntegerBinding
import moe.feng.common.arch.list.BindingListViewAdapter
import moe.feng.common.arch.list.bind

class SampleListAdapter : BindingListViewAdapter(BR.item) {

    init {
        bind(StringBinder())
        bind<Int, SampleItemIntegerBinding>(R.layout.sample_item_integer)
        bindSelf(SimpleCardBinder.INSTANCE)
        bindSelf(AnotherSimpleCardBinder.INSTANCE)
    }

    companion object {

        @JvmStatic fun newInstance(): SampleListAdapter = SampleListAdapter()

    }

}