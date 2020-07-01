package com.yh.video.pirate.utils

import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.base.LoadStateAdapter

/**
 * 加载底部Adapter
 */
fun <T:BaseAdapter<*>> T.loadStateAdapter():T{
    this.withLoadStateFooter(LoadStateAdapter(this))
    return this
}