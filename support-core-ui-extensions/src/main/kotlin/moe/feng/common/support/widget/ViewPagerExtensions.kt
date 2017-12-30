@file:JvmName("ViewPagerUtils")
package moe.feng.common.support.widget

import android.databinding.BindingAdapter
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager

@BindingAdapter("bind:pagerAdapter")
fun setPagerAdapter(pager: ViewPager, adapter: PagerAdapter) {
    pager.adapter = adapter
}