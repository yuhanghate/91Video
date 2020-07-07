package com.yh.video.pirate.ui.main.adapter

import android.view.ViewGroup
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.repository.network.result.Discover
import com.yh.video.pirate.ui.main.viewholder.ItemDiscoverVH
import com.yh.video.pirate.base.BaseViewHolder

/**
 * 发现
 */
class DiscoverAdapter:BaseAdapter<Discover>(Discover.diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Discover, *> {
        return ItemDiscoverVH(parent)
    }
}