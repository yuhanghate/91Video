package com.yh.video.pirate.ui.main.adapter

import android.view.ViewGroup
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.repository.network.result.Video
import com.yh.video.pirate.ui.main.viewholder.ItemDiscoverVH
import com.yuhang.novel.pirate.base.BaseViewHolder

/**
 * 发现
 */
class DiscoverAdapter:BaseAdapter<Video>(Video.diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<Video, *> {
        return ItemDiscoverVH(parent)
    }
}