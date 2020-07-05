package com.yh.video.pirate.ui.history.adapter

import android.view.ViewGroup
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.repository.network.result.Video
import com.yh.video.pirate.ui.history.viewholder.ItemHistoryVH
import com.yuhang.novel.pirate.base.BaseViewHolder

class HistoryAdapter:BaseAdapter<Video>(Video.diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Video, *> {
        return ItemHistoryVH(parent)
    }
}