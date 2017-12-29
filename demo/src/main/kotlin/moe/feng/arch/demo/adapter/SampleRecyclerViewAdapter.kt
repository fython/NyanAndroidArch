package moe.feng.arch.demo.adapter

import moe.feng.common.arch.demo.BR
import moe.feng.common.arch.demo.R
import moe.feng.common.arch.demo.databinding.SampleItemIntegerBinding
import moe.feng.common.arch.list.BindingRecyclerViewAdapter
import moe.feng.common.arch.list.bind

class SampleRecyclerViewAdapter : BindingRecyclerViewAdapter(BR.item) {

    init {
        bind(StringBinder())
        bind<Int, SampleItemIntegerBinding>(R.layout.sample_item_integer)
        bindSelf(SimpleCardBinder.INSTANCE)
        bindSelf(AnotherSimpleCardBinder.INSTANCE)
    }

}
