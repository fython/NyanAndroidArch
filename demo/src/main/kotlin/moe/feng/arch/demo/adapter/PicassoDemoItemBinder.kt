package moe.feng.arch.demo.adapter

import android.net.Uri
import moe.feng.common.arch.demo.R
import moe.feng.common.arch.demo.databinding.SampleItemPicassoBinding
import moe.feng.common.arch.list.BindingItemBinder

class PicassoDemoItemBinder
    : BindingItemBinder<Uri, SampleItemPicassoBinding>(R.layout.sample_item_picasso)