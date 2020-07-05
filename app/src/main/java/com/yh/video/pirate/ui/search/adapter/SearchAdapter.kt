package com.yh.video.pirate.ui.search.adapter

import android.view.ViewGroup
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.repository.network.result.Search
import com.yh.video.pirate.ui.search.viewholder.ItemSearchVH
import com.yuhang.novel.pirate.base.BaseViewHolder

class SearchAdapter:BaseAdapter<Search>(Search.diffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Search, *> {
        return ItemSearchVH(parent)
    }
}