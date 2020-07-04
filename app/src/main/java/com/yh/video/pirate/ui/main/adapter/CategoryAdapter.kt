package com.yh.video.pirate.ui.main.adapter

import android.view.ViewGroup
import com.yh.video.pirate.base.BaseAdapter
import com.yh.video.pirate.repository.network.result.VideoType
import com.yh.video.pirate.ui.main.viewholder.ItemCategoryVH
import com.yuhang.novel.pirate.base.BaseViewHolder

/**
 * 分类
 */
class CategoryAdapter:BaseAdapter<VideoType>(VideoType.diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<VideoType, *> {
        return ItemCategoryVH(parent)
    }
}