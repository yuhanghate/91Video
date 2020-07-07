package com.yh.video.pirate.utils

import androidx.recyclerview.widget.ConcatAdapter
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.base.LoadStateAdapter

///**
// * 加载底部Adapter
// */
//fun <T:BaseAdapter<*>> T.loadStateAdapter():T{
//    this.withLoadStateHeaderAndFooter(footer = LoadStateAdapter(this), header = LoadStateAdapter(this))
//    return this
//}

/**
 * 加载底部Adapter
 */
fun <T:BaseAdapter<*>> T.loadFooterAdapter(): ConcatAdapter {
    return this.withLoadStateFooter(footer = LoadStateAdapter(this))
}